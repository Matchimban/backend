package com.project.matchimban.api.reservation.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class ReservationCreateGetFormRequest {

    @Schema(description = "매장 id")
    @NotNull @Positive
    Long restaurantId;

    @Schema(description = "조회할 날짜")
    @NotNull
    LocalDate rstDate;
}
