package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreateGetFormRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationForUserDto;
import com.project.matchimban.api.reservation.domain.dto.ReservationFormDto;

import java.util.List;

public interface ReservationRepositoryQuerydsl {
    List<ReservationFormDto> getReservationListByDate(ReservationCreateGetFormRequest dto);
    List<ReservationForUserDto> getReservationListForUser(Long userId);
}
