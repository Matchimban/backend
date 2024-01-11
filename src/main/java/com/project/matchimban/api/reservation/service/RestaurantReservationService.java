package com.project.matchimban.api.reservation.service;

import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface RestaurantReservationService {
    ResponseEntity createRestaurantReservation(RestaurantReservationCreateRequest dto);

    ResponseEntity getRestaurantReservation(Long restaurantId);

    ResponseEntity updateRestaurantReservation(RestaurantReservationUpdateRequest dto);
}
