package com.project.matchimban.api.reservation.service.impl;

import com.project.matchimban.api.menu.domain.Menu;
import com.project.matchimban.api.menu.repository.MenuRepository;
import com.project.matchimban.api.reservation.domain.dto.ReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationUpdateToFailAndRefundRequest;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.reservation.repository.ReservationMenuRepository;
import com.project.matchimban.api.reservation.repository.ReservationRepository;
import com.project.matchimban.api.reservation.repository.RestaurantReservationRepository;
import com.project.matchimban.api.reservation.service.ReservationService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    private IamportClient iamportClient;


    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(iamportApiKey, iamportApiSecret);
    }

    @Value("${iamport.client.apiKey}")
    private String iamportApiKey;
    @Value("${iamport.client.apiSecret}")
    private String iamportApiSecret;

    @Transactional(noRollbackFor=RuntimeException.class)
    @Override
    public ResponseEntity createReservation(ReservationCreateRequest dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_USER_NONE_PK));

        RestaurantReservation restaurantReservation = restaurantReservationRepository.findFirstByRestaurantId(dto.getRestaurantId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_RESTAURANTRESERVATION_NONE_PK));

        Reservation reservation = dto.toReservationInitEntity(user, restaurantReservation);
        reservationRepository.save(reservation);
        List<ReservationMenu> menuList = dto.toReservationMenuEntityList(reservation);
        reservationMenuRepository.saveAll(menuList);


        IamportResponse<Payment> iamportPayInfo = null;
        try {
            iamportPayInfo = iamportClient.paymentByImpUid(dto.getImpUid());
        } catch (Exception e) {
            log.error("Import Access Error");
            reservation.changeStatusByImportAccessFail();
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_IAMPORT);
        }

        if(isInvalidVerifyPayService(reservation, iamportPayInfo.getResponse(), dto)){ //실패
            log.error("결제 값 Invalid verify Error");
            reservation.changeStatusByInvalidVerify();
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_INVALID_VERIFY);
        } else { //성공
            reservation.changeStatusBySuccess();
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
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            iamportClient.cancelPaymentByImpUid(new CancelData(dto.getImpUid(), true));
        } catch (Exception e) {
            log.error("결제가 됐으나 에러로 인해 환불 실패");
            throw new SVCException(ErrorConstant.RESERVATION_ERROR_IAMPORT);
        }

        Reservation reservation = reservationRepository.findByImpUid(dto.getImpUid())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESERVATION_ERROR_NONE_PK));
        reservation.changeStatusByFailAndRefund();
        return new ResponseEntity(new ResultData(), httpStatus);
    }

}
