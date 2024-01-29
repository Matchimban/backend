package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    Optional<Reservation> findByImpUid(String impUid);

}
