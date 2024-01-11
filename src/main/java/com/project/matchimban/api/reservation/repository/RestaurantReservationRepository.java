package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantReservationRepository extends JpaRepository<RestaurantReservation, Long> {
    Optional<RestaurantReservation> findFirstByRestaurantId(Long restaurantId);
}
