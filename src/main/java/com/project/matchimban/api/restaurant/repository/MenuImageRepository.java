package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.entity.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuImageRepository extends JpaRepository<MenuImage, Long> {
}
