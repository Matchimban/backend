package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.emums.RestaurantReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.ReservationSeat;
import com.project.matchimban.api.reservation.domain.entity.ReservationTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "매장_예약 조화 응답 DTO")
@Data
public class RestaurantReservationGetResponse {
    @Schema(description = "예약 가능한 시간들")
    List<TimeData> reservationTimeList = new ArrayList<>();

    @Schema(description = "예약 가능한 잔여석의 개수 및 크기")
    List<SeatData> reservationSeatList = new ArrayList<>();

    @Schema(description = "활성화 정보")
    RestaurantReservationStatus status;

    @Data
    public static class TimeData{
        @Schema(description = "예약 가능한 시작 시간", example = "12:00:00", type = "string")
        private LocalTime rstTime;

        TimeData(ReservationTime time){
            this.rstTime = time.getRstTime();
        }
    }
    @Data
    public static class SeatData{
        private int cnt;
        private int size;

        SeatData(ReservationSeat seat){
            this.cnt = seat.getCnt();
            this.size = seat.getSize();
        }
    }

    public void changeTimeList(List<ReservationTime> reservationTimeList){
        this.reservationTimeList.addAll(
                reservationTimeList.stream()
                        .map(t -> new TimeData(t))
                        .collect(Collectors.toList()));
    }
    public void changeTableList(List<ReservationSeat> reservationSeatList){
        this.reservationSeatList.addAll(
                reservationSeatList.stream()
                        .map(t -> new SeatData(t))
                        .collect(Collectors.toList()));
    }
}
