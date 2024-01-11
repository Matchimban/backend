package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.ReservationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationTableRepository extends JpaRepository<ReservationTable, Long> {
    @Modifying
    @Query(value = "delete from reservation_table r where r.restaurant_reservation_id = :restaurantReservationId", nativeQuery = true)
    void deleteByRestaurantReservationId(@Param("restaurantReservationId") Long restaurantReservationId);
}
