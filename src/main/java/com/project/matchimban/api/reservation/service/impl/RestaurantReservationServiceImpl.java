package com.project.matchimban.api.reservation.service.impl;


import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationGetResponse;
import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationUpdateRequest;
import com.project.matchimban.api.reservation.domain.emums.RestaurantReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.ReservationSeat;
import com.project.matchimban.api.reservation.domain.entity.ReservationTime;
import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.reservation.repository.ReservationSeatRepository;
import com.project.matchimban.api.reservation.repository.ReservationTimeRepository;
import com.project.matchimban.api.reservation.repository.RestaurantReservationRepository;
import com.project.matchimban.api.reservation.service.RestaurantReservationService;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantReservationServiceImpl implements RestaurantReservationService {

    private final RestaurantReservationRepository restaurantReservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationSeatRepository reservationSeatRepository;

    @Transactional
    @Override
    public ResponseEntity createRestaurantReservation(RestaurantReservationCreateRequest dto) {

        //1. 매장 pk 확인
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESTAURANTRESERVATION_ERROR_RESTAURANT_NONE_PK));

        //2. 매장_예약 존재 여부 확인
        if(restaurantReservationRepository.count() >= 1)
            new SVCException(ErrorConstant.RESTAURANTRESERVATION_ERROR_ALREADY_EXISTS);

        //3. 매장_예약 데이터 생성
        RestaurantReservation rstRsv =
                RestaurantReservation.createRestaurantReservation(restaurant, RestaurantReservationStatus.ACTIVE);
        restaurantReservationRepository.save(rstRsv);


        //3. 예약 가능한 시간 데이터 생성
        dto.getReservationTimeList().stream().forEach(
                t-> {
                    ReservationTime rsvTime = ReservationTime.createReservationTime(rstRsv, t.getRstTime());
                    reservationTimeRepository.save(rsvTime);
                }
        );

        //4. 예약 가능한 테이블 데이터 생성
        dto.getReservationSeatList().stream().forEach(
                t-> {
                    ReservationSeat rsvTable = ReservationSeat.createReservationSeat(rstRsv, t.getSize(), t.getCnt());
                    reservationSeatRepository.save(rsvTable);
                }
        );

        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }

    public ResponseEntity getRestaurantReservation(Long restaurantId){
        ResultData resultData = new ResultData();
        RestaurantReservationGetResponse result = new RestaurantReservationGetResponse();

        //1. 매장 id로 매장_예약 테이블 존재 확인
        RestaurantReservation rstRsv = restaurantReservationRepository.findFirstByRestaurantId(restaurantId)
                .orElseThrow(() -> new SVCException(ErrorConstant.RESTAURANTRESERVATION_ERROR_NONE_PK));

        //2. 예약 가능한 매장시간 조회
        result.changeTimeList(rstRsv.getReservationTimes());

        //3. 예약 가능한 매장테이블 조회
        result.changeTableList(rstRsv.getReservationSeats());

        result.setStatus(rstRsv.getStatus());

        resultData.setResult(result);
        return new ResponseEntity(resultData, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateRestaurantReservation(RestaurantReservationUpdateRequest dto){
        RestaurantReservation rstRsv = restaurantReservationRepository.findFirstByRestaurantId(dto.getRestaurantId())
                .orElseThrow(() -> new SVCException(ErrorConstant.RESTAURANTRESERVATION_ERROR_NONE_PK));

        rstRsv.changeStatus(dto.getStatus());
        reservationSeatRepository.deleteByRestaurantReservationId(rstRsv.getId());
        reservationTimeRepository.deleteByRestaurantReservationId(rstRsv.getId());

        //재설정
        dto.getReservationTimeList().stream().forEach(
                t-> {
                    ReservationTime rsvTime = ReservationTime.createReservationTime(rstRsv, t.getStartTime());
                    reservationTimeRepository.save(rsvTime);
                }
        );
        dto.getReservationTableList().stream().forEach(
                t-> {
                    ReservationSeat rsvSeat = ReservationSeat.createReservationSeat(rstRsv, t.getSize(), t.getCnt());
                    reservationSeatRepository.save(rsvSeat);
                }
        );

        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }
}
