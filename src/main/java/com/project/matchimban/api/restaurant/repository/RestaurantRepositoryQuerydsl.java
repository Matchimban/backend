package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;

import java.util.List;

public interface RestaurantRepositoryQuerydsl {
    List<RestaurantReadResponse> getRestaurantsLeftJoinImage();
}
