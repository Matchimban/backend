package com.project.matchimban.api.reservation.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ReservationFormDto {

    private LocalTime rstTime;
    private Integer size;

    @QueryProjection
    public ReservationFormDto(LocalTime rstTime, Integer size){
        this.rstTime =rstTime;
        this.size =size;
    }
}
