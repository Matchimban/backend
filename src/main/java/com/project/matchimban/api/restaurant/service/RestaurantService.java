package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantImageCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantRegisterRequest;

import java.util.List;

public interface RestaurantService {
    void registerRestaurant(RestaurantRegisterRequest request, CustomUserDetails userDetails);
    void registerRestaurantImage(Long restaurantId, List<RestaurantImageCreateRequest> images);
}
