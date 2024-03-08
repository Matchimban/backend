package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantRegisterRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantsReadResponse;

import java.util.List;

public interface RestaurantService {
    void registerRestaurant(RestaurantRegisterRequest request, CustomUserDetails userDetails);
    List<RestaurantsReadResponse> getRestaurants();
}
