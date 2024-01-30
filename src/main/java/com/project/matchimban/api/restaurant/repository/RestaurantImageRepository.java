package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
}