package com.project.matchimban.api.reservation.domain.dto;

import lombok.Data;

@Data
public class ReservationUpdateToRefundForOwnerRequest {
    Long reservationId;
    int refundAmount;
}
