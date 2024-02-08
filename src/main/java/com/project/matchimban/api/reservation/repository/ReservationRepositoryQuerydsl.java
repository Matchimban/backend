package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationRepositoryQuerydsl {
    List<ReservationFormDto> getReservationListByDate(ReservationCreateGetFormRequest dto);
    List<ReservationForUserDto> getReservationListForUser(Long userId);
    Long getCntOfReservation(ReservationCreatePreRequest dto);
    Page<ReservationForOwnerDto> getReservationListForOwner(Pageable pageable, Long restaurantId);
}
