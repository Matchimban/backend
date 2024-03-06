package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReservationForOwnerDto {

    Long userId;
    String userName;
    String userNickName;
    String userPhone;

    Integer size; //선택테이블 크기
    LocalDate rstDate; //예약일
    LocalTime rstTime; //예약시간
    ReservationStatus status; //상태

    Integer regularPrice; //정가
    Integer paymentAmount; //결제가
    Integer refundAmount; //환불금

    List<ReservationMenuForOwnerDto> menuList = new ArrayList<>();

    @QueryProjection
    public ReservationForOwnerDto(Reservation reservation){
        this.userId = reservation.getUser().getId();
        this.userName = reservation.getUser().getName();
        this.userNickName = reservation.getUser().getNickname();
        this.userPhone = reservation.getUser().getPhone();

        this.size = reservation.getSize();
        this.rstDate = reservation.getRstDate();
        this.rstTime = reservation.getRstTime();

        this.regularPrice = reservation.getRegularPrice();
        this.paymentAmount = reservation.getPaymentAmount();
        this.refundAmount = reservation.getRefundAmount();
        this.status = reservation.getStatus();

        //메뉴 추가
        this.menuList = reservation.getReservationMenus().stream()
                .map(m -> new ReservationMenuForOwnerDto(m)).collect(Collectors.toList());
    }
}
