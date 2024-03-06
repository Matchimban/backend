package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import lombok.Data;

@Data
public class ReservationMenuForOwnerDto {
    String name;
    int regularPrice;
    int paymentAmount;

    public ReservationMenuForOwnerDto(ReservationMenu reservationMenu){
        this.name = reservationMenu.getMenuName();
        this.regularPrice = reservationMenu.getRegularPrice();
        this.paymentAmount = reservationMenu.getPaymentAmount();
    }
}
