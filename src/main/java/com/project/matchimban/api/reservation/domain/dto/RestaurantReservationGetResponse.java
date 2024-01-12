package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.emums.RestaurantReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.ReservationTable;
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

    @Schema(description = "예약 가능한 테이블의 개수 및 크기")
    List<TableData> reservationTableList = new ArrayList<>();

    @Schema(description = "활성화 정보")
    RestaurantReservationStatus status;

    @Data
    public static class TimeData{
        @Schema(description = "예약 가능한 시작 시간", example = "12:00:00", type = "string")
        private LocalTime startTime;
        @Schema(description = "예약 가능한 시작 시간", example = "12:00:00", type = "string")
        private LocalTime endTime;

        TimeData(ReservationTime time){
            this.startTime = time.getStartTime();
            this.endTime = time.getEndTime();
        }
    }
    @Data
    public static class TableData{
        private int cnt;
        private int size;

        TableData(ReservationTable table){
            this.cnt = table.getCnt();
            this.size = table.getSize();
        }
    }

    public void changeTimeList(List<ReservationTime> reservationTimeList){
        this.reservationTimeList.addAll(
                reservationTimeList.stream()
                        .map(t -> new TimeData(t))
                        .collect(Collectors.toList()));
    }
    public void changeTableList(List<ReservationTable> reservationTableList){
        this.reservationTableList.addAll(
                reservationTableList.stream()
                        .map(t -> new TableData(t))
                        .collect(Collectors.toList()));
    }
}
