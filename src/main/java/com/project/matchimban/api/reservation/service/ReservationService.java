package com.project.matchimban.api.reservation.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.reservation.domain.dto.*;
import org.springframework.http.ResponseEntity;

public interface ReservationService {

    ResponseEntity createReservationAndValid(ReservationCreateRequest dto);
    ResponseEntity updateReservationOfFailAndRefund(ReservationUpdateToFailAndRefundRequest dto);
    ResponseEntity getReservationCreateForm(ReservationCreateGetFormRequest dto);
    ResponseEntity updateReservationToRefund(ReservationUpdateToRefundRequest dto);
    ResponseEntity getReservationListForUser(CustomUserDetails currentUser);
}
