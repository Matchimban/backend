package com.project.matchimban.api.reservation.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "매장_예약 등록 요청 DTO")
@Data
public class RestaurantReservationCreateRequest {

    @Schema(description = "매장 id")
    @NotNull @Positive
    Long restaurantId;

    @Schema(description = "예약 가능한 시간들")
    List<ReservationTime> reservationTimeList;

    @Schema(description = "예약 가능한 테이블의 개수 및 크기")
    List<ReservationTable> reservationTableList;

    @Data
    public static class ReservationTime{
        @Schema(description = "예약 가능한 시작 시간", example = "12:00:00", type = "string")
        private LocalTime startTime;
        @Schema(description = "예약 가능한 종료 시간", example = "13:00:00", type = "string")
        private LocalTime endTime;
    }
    @Data
    public static class ReservationTable{
        private int cnt;
        private int size;
    }

}
