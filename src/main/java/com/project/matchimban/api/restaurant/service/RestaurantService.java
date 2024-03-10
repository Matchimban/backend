package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantRegisterRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantDetailReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantsReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    void registerRestaurant(RestaurantRegisterRequest request, CustomUserDetails userDetails);
    List<RestaurantReadResponse> getRestaurants();
    RestaurantDetailReadResponse getRestaurant(Long id);
    void updateRestaurant(Long id, RestaurantUpdateRequest request);
}
