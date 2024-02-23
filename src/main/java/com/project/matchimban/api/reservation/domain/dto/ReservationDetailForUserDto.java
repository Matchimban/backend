package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReservationDetailForUserDto {


    Long reservationId;

    Integer size; //선택테이블 크기
    LocalDate rstDate; //예약일
    LocalTime rstTime; //예약시간
    LocalDateTime cancelDate; //취소일

    Integer regularPrice; //정가
    Integer paymentAmount; //결제가
    Integer refundAmount; //환불금
    ReservationStatus status; //상태

    List<Menu> menuList = new ArrayList<>(); //메뉴 리스트

    @Data
    @Builder
    public static class Menu{
        private Long menuId;
        private String menuName;
        private int regularPrice;
        private int paymentAmount;
    }

    public ReservationDetailForUserDto(Reservation reservation){
        this.reservationId = reservation.getId();
        this.size = reservation.getSize();
        this.rstDate = reservation.getRstDate();
        this.rstTime = reservation.getRstTime();

        this.regularPrice = reservation.getRegularPrice();
        this.paymentAmount = reservation.getPaymentAmount();
        this.refundAmount = reservation.getRefundAmount();
        this.status = reservation.getStatus();

        this.menuList = reservation.getReservationMenus().stream()
                .map(m -> Menu.builder()
                        .menuId(m.getId())
                        .menuName(m.getMenuName())
                        .regularPrice(m.getRegularPrice())
                        .paymentAmount(m.getPaymentAmount())
                        .build()
                ).collect(Collectors.toList());
    }
}
