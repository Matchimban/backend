package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
