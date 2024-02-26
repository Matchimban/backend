package com.project.matchimban.api.reservation.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ReservationUpdateToRefundRequest {

    @Schema(description = "환불 금액")
    @NotNull @Positive
    Integer amount;

    @Schema(description = "예약 id")
    @NotNull @Positive
    Long reservationId;

}
