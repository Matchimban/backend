package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.dto.RestaurantsReadResponse;

import java.util.List;

public interface RestaurantRepositoryQuerydsl {
    List<RestaurantsReadResponse> getRestaurantsLeftJoinImage();
}
