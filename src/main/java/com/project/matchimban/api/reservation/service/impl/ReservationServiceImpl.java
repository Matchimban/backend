package com.project.matchimban.api.reservation.service.impl;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.menu.dto.entity.Menu;
import com.project.matchimban.api.menu.repository.MenuRepository;
import com.project.matchimban.api.reservation.domain.dto.*;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.reservation.repository.*;
import com.project.matchimban.api.reservation.service.ReservationService;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.response.ResultData;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMenuRepository reservationMenuRepository;
    private final RestaurantReservationRepository restaurantReservationRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    private IamportClient iamportClient;


    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(iamportApiKey, iamportApiSecret);
    }

    @Value("${iamport.client.apiKey}")
    private String iamportApiKey;
    @Value("${iamport.client.apiSecret}")
    private String iamportApiSecret;

    @Transactional
    public ResponseEntity createPreReservation(ReservationCreatePreRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_USER_NONE_PK));

        RestaurantReservation restaurantReservation = restaurantReservationRepository.findFirstByRestaurantId(dto.getRestaurantId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_RESTAURANTRESERVATION_NONE_PK));

        if(!isReservationPossible(dto, restaurantReservation))
            new SVCException(ErrorConstant.RESERVATION_NO_REMAIN_SEAT);

        Reservation reservation = dto.toReservationInitEntity(user, restaurantReservation);
        reservationRepository.save(reservation);
        List<ReservationMenu> menuList = dto.toReservationMenuEntityList(reservation);
        reservationMenuRepository.saveAll(menuList);

        ReservationCreatePreResponse res = ReservationCreatePreResponse.builder()
                .reservationId(reservation.getId()).build();

        ResultData resultData = new ResultData();
        resultData.setResult(res);
        return new ResponseEntity(resultData, HttpStatus.OK);
    }



    @Transactional(noRollbackFor=RuntimeException.class)
    @Override
    public ResponseEntity createReservationAndValid(ReservationCreateRequest dto){
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_NONE_PK));

        //ING
        IamportResponse<Payment> iamportPayInfo = null;
        try {
            iamportPayInfo = iamportClient.paymentByImpUid(dto.getImpUid());
        } catch (Exception e) {
            log.error("Import Access Error");
            reservation.changeStatusByImportAccessFail(); //FAIL_AND_NOT_REFUND
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_IAMPORT);
        }

        if(isInvalidVerifyPayService(reservation, iamportPayInfo.getResponse(), dto)){ //실패
            log.error("결제 값 Invalid verify Error");
            reservation.changeStatusByInvalidVerify(); //FAIL_AND_REFUND
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_INVALID_VERIFY);
        } else { //성공
            reservation.changeStatusBySuccess(); //SUCCESS
        }
        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }

    private boolean isInvalidVerifyPayService(Reservation reservation, Payment payment, ReservationCreateRequest dto){
        List<Long> menuIdList = dto.getMenuList().stream().map(m -> m.getMenuId()).collect(Collectors.toList());
        List<Menu> DBMenuList = menuRepository.findAllById(menuIdList);

        int DBPriceTotal = DBMenuList.stream().map(m -> m.getPrice()).reduce(0, Integer::sum); //정가 가격
        if(payment.getAmount().intValue()  != DBPriceTotal) {
            log.error("Iamport 결제 값 != DB_Menu 가격의 합");
            return true;
        }
        if (payment.getAmount().intValue() != reservation.getPaymentAmount()) {
            log.error("Iamport 결제 값 != DB_Reservation 결제 값");
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public ResponseEntity updateReservationOfFailAndRefund(ReservationUpdateToFailAndRefundRequest dto){
        try {
            iamportClient.cancelPaymentByImpUid(new CancelData(dto.getImpUid(), true));
        } catch (Exception e) {
            log.error("결제가 됐으나 에러로 인해 환불 실패");
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_IAMPORT);
        }

        Reservation reservation = reservationRepository.findByImpUid(dto.getImpUid())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_NONE_PK));
        reservation.changeStatusByFailAndRefund();
        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }

    public ResponseEntity getReservationCreateForm(ReservationCreateGetFormRequest dto){
        ResultData resultData = new ResultData();

        ReservationCreateGetFormResponse returnData = new ReservationCreateGetFormResponse();

        RestaurantReservation restaurantReservation = restaurantReservationRepository.findFirstByRestaurantId(dto.getRestaurantId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_RESTAURANTRESERVATION_NONE_PK));
        List<ReservationFormDto> reservationListByDate = reservationRepository.getReservationListByDate(dto);

        returnData.changeAvailInfo(restaurantReservation, dto, reservationListByDate);

        //메뉴 정보 호출은 추후 논의

        resultData.setResult(returnData);
        return new ResponseEntity(resultData, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity updateReservationToRefund(ReservationUpdateToRefundRequest dto){
        //환불
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_NONE_PK));

        //예약일과 현재일 비교해 환불값 측정
        Integer refundAmount = reservation.calculateRefundAmount();
        if(refundAmount==0) new SVCException(ErrorConstant.RESERVATION_IMPOSSIBLE_REFUND);

        //아임포트 환불하기
        IamportResponse<Payment> iamportPayInfo = null;
        try {
            if (reservation.getPaymentAmount() == refundAmount) { //전체환불
                iamportClient.cancelPaymentByImpUid(new CancelData(reservation.getImpUid(), true));
            } else { //부분환불
                iamportClient.cancelPaymentByImpUid(new CancelData(reservation.getImpUid(), true, new BigDecimal(refundAmount)));
            }
            //환불내역 저장
            reservation.changeStatusByRefund(refundAmount);
        }catch (Exception e){
            new SVCException(ErrorConstant.RESERVATION_ERROR_IAMPORT);
        }

        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }


    public ResponseEntity getReservationListForUser(CustomUserDetails currentUser, Pageable pageable){
        ResultData resultData = new ResultData();
        resultData.setResult(reservationRepository.getReservationListForUser(pageable, currentUser.getUserId()));

        return new ResponseEntity(resultData, HttpStatus.OK);
    }

    public ResponseEntity getReservationDetailForUser(CustomUserDetails currentUser, Long reservationId){
        ResultData resultData = new ResultData();
        Reservation rsv = reservationRepository.getReservationDetailForUser(reservationId);
        ReservationDetailForUserDto detailData = new ReservationDetailForUserDto(rsv);
        resultData.setResult(detailData);
        return new ResponseEntity(resultData, HttpStatus.OK);
    }

    private boolean isReservationPossible(ReservationCreatePreRequest dto, RestaurantReservation rstRsv){
        //가능 총개수
        Integer totalCnt = rstRsv.getReservationSeats().stream()
                .filter(s -> s.getSize() == dto.getSize())
                .findFirst().get().getCnt();

        //예약된 개수
        Long curCnt = reservationRepository.getCntOfReservation(dto);

        if(totalCnt > curCnt) return true;
        return false;
    }

    public ResponseEntity getReservationListForOwner(CustomUserDetails currentUser, Pageable pageable, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_RESTAURANT_NONE_PK));
        if(restaurant.getUser().getId() != currentUser.getUserId())
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_OWNER_GET_INVALID_VERIFY);

        ResultData resultData = new ResultData();
        resultData.setResult(reservationRepository.getReservationListForOwner(pageable, restaurantId));
        return new ResponseEntity(resultData, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity updateReservationToRefundForOwner(CustomUserDetails currentUser, ReservationUpdateToRefundForOwnerRequest dto) {
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_NONE_PK));

        if(currentUser.getUserId()!=reservation.getRestaurantReservation().getRestaurant().getUser().getId()){
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_OWNER_REFUND_INVALID_VERIFY);
        }

        IamportResponse<Payment> iamportPayInfo = null;
        try {
            iamportClient.cancelPaymentByImpUid(new CancelData(reservation.getImpUid(), true));
            reservation.changeStatusByOwnerRefund(dto.getRefundAmount());
        }catch (Exception e){
            new SVCException(ErrorConstant.RESERVATION_ERROR_IAMPORT);
        }

        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }
}
