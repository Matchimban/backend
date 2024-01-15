package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    ResponseEntity<Object> createRestaurant(RestaurantCreateRequest dto);
}
