package com.project.matchimban.api.reservation.controller;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationUpdateToFailAndRefundRequest;
import com.project.matchimban.api.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "Reservation", description = "예약 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "(예약)예약 등록", description = "예약을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "예약 성공"),
            @ApiResponse(responseCode = "40000-63001", description = "실패: 예약자 pk 조회 오류"),
            @ApiResponse(responseCode = "40000-63002", description = "실패: 매장_예약 pk 조회 오류"),
            @ApiResponse(responseCode = "40000-63003", description = "실패: iamport 통신 오류"),
            @ApiResponse(responseCode = "40000-63004", description = "실패: 결제 검증 오류"),
    })
    @PostMapping("/api/reservations")
    public ResponseEntity createReservation(@Validated @RequestBody ReservationCreateRequest dto){
        return reservationService.createReservation(dto);
    }

    @Operation(summary = "(예약)에러 시 환불 요청", description = "결제를 완료했지만 에러가 발생할 시 환불을 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "환불 성공"),
            @ApiResponse(responseCode = "40000-63003", description = "실패: iamport 통신 오류"),
            @ApiResponse(responseCode = "40000-63005", description = "실패: 환불은 됐으나 DB 업데이트 실패한 경우"),
    })
    @PatchMapping("/api/reservations/fail-and-refund")
    public ResponseEntity updateReservationToFailAndRefund(@Validated @RequestBody ReservationUpdateToFailAndRefundRequest dto){
        return reservationService.updateReservationOfFailAndRefund(dto);
    }

}