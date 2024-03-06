package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
