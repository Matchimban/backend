package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.*;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationRepositoryQuerydsl {
    List<ReservationFormDto> getReservationListByDate(ReservationCreateGetFormRequest dto);
    Page<ReservationForUserDto> getReservationListForUser(Pageable pageable, Long userId);
    Reservation getReservationDetailForUser(Long reservationId);
    Long getCntOfReservation(ReservationCreatePreRequest dto);
    Page<ReservationForOwnerDto> getReservationListForOwner(Pageable pageable, Long restaurantId);
}
