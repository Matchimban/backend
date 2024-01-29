package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreateGetFormRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationFormDto;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<ReservationFormDto> getReservationListByDate(ReservationCreateGetFormRequest dto);
}
