package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long>, ReservationSeatRepositoryCustom {

}
