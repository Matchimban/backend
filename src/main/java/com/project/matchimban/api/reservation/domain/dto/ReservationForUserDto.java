package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ReservationForUserDto {

    Long reservationId;

    Integer size; //선택테이블 크기
    LocalDate rstDate; //예약일
    LocalTime rstTime; //예약시간
    LocalDateTime cancelDate; //취소일

    Integer regularPrice; //정가
    Integer paymentAmount; //결제가
    Integer refundAmount; //환불금
    ReservationStatus status; //상태

    @QueryProjection
    public ReservationForUserDto(Long reservationId, Integer size, LocalDate rstDate, LocalTime rstTime, LocalDateTime cancelDate,
                                 Integer regularPrice, Integer paymentAmount, Integer refundAmount, ReservationStatus status){
        this.reservationId = reservationId;
        this.size = size;
        this.rstDate = rstDate;
        this.rstTime = rstTime;
        this.cancelDate = cancelDate;
        this.regularPrice = regularPrice;
        this.paymentAmount = paymentAmount;
        this.refundAmount = refundAmount;
        this.status = status;
    }
}
