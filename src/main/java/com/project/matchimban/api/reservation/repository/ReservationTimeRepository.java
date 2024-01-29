package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long>, ReservationTimeRepositoryCustom {

}
