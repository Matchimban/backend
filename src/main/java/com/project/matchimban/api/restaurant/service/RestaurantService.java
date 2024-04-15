package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantStatusUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantsReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    void registerRestaurant(RestaurantCreateRequest request, CustomUserDetails userDetails);

    List<RestaurantsReadResponse> getRestaurants();

    RestaurantReadResponse getRestaurant(Long id);

    void updateRestaurant(Long id, RestaurantUpdateRequest request);

    Restaurant validateRestaurantId(Long id);

    void changeRestaurantStatus(Long id, RestaurantStatusUpdateRequest dto, CustomUserDetails userDetails);
}
