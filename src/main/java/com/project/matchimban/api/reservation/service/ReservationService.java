package com.project.matchimban.api.reservation.service;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreateGetFormRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationUpdateToFailAndRefundRequest;
import com.project.matchimban.api.reservation.domain.dto.ReservationUpdateToRefundRequest;
import org.springframework.http.ResponseEntity;

public interface ReservationService {


    ResponseEntity createReservation(ReservationCreateRequest dto);
    ResponseEntity updateReservationOfFailAndRefund(ReservationUpdateToFailAndRefundRequest dto);
    ResponseEntity getReservationCreateForm(ReservationCreateGetFormRequest dto);
    ResponseEntity updateReservationToRefund(ReservationUpdateToRefundRequest dto);
}
