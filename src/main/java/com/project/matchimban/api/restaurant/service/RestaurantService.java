package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantImageCreateRequest;

import java.util.List;

public interface RestaurantService {
    void createRestaurant(RestaurantCreateRequest dto, CustomUserDetails userDetails);
    void registerRestaurantImage(Long restaurantId, List<RestaurantImageCreateRequest> images);
}
