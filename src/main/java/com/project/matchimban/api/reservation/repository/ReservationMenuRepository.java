package com.project.matchimban.api.reservation.repository;

import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationMenuRepository extends JpaRepository<ReservationMenu, Long> {

}
