package com.project.matchimban.api.reservation.service;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationUpdateToFailAndRefundRequest;
import org.springframework.http.ResponseEntity;

public interface ReservationService {


    ResponseEntity createReservation(ReservationCreateRequest dto);
    ResponseEntity updateReservationOfFailAndRefund(ReservationUpdateToFailAndRefundRequest dto);
}
