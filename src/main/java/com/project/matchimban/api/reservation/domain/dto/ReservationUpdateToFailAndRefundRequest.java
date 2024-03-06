package com.project.matchimban.api.reservation.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "환불 요청 DTO(결제 완료 후 에러 발생 시)")
@Data
public class ReservationUpdateToFailAndRefundRequest {

    @NotBlank
    String ImpUid;
}
