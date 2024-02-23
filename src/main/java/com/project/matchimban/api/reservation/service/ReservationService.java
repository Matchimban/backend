package com.project.matchimban.api.reservation.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.reservation.domain.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ReservationService {

    ResponseEntity createReservationAndValid(ReservationCreateRequest dto);
    ResponseEntity updateReservationOfFailAndRefund(ReservationUpdateToFailAndRefundRequest dto);
    ResponseEntity getReservationCreateForm(ReservationCreateGetFormRequest dto);
    ResponseEntity updateReservationToRefund(ReservationUpdateToRefundRequest dto);
    ResponseEntity getReservationListForUser(CustomUserDetails currentUser, Pageable pageable);
    ResponseEntity getReservationDetailForUser(CustomUserDetails currentUser, Long reservationId);
    ResponseEntity getReservationListForOwner(CustomUserDetails currentUser, Pageable pageable, Long restaurantId);
    ResponseEntity updateReservationToRefundForOwner(CustomUserDetails currentUser, ReservationUpdateToRefundForOwnerRequest dto);
}
