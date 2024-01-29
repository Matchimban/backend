package com.project.matchimban.api.reservation.repository;


public interface ReservationSeatRepositoryCustom {
    void deleteByRestaurantReservationId(Long restaurantReservationId);
}
