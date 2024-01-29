package com.project.matchimban.api.reservation.domain.dto;

import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Data
public class ReservationCreateGetFormResponse {
    @Schema(description = "매장 id")
    Long restaurantId;

    @Schema(description = "예약 가능한 시간에 대한 잔여석 정보들")
    Map<LocalTime, HashMap<Integer, Integer>> timeInfo = new HashMap<>();


    public void changeAvailInfo(RestaurantReservation rstRsv, ReservationCreateGetFormRequest requestDto, List<ReservationFormDto> reservationListByDate) {
        this.restaurantId = requestDto.getRestaurantId();
        rstRsv.getReservationTimes().forEach(t -> this.timeInfo.put(t.getRstTime(), (HashMap<Integer, Integer>) createSeatMap(rstRsv)));
        reservationListByDate.forEach(r -> {
            this.timeInfo.get(r.getRstTime()).put(r.getSize(), this.timeInfo.get(r.getRstTime()).get(r.getSize()) - 1);
        });
    }
    private Map<Integer, Integer> createSeatMap(RestaurantReservation rstRsv){
        return rstRsv.getReservationSeats().stream()
                .collect(Collectors.toMap(
                        i -> i.getSize(),
                        i -> i.getCnt())
                );
    }
}
