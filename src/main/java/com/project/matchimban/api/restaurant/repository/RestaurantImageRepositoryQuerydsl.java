package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantImageReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantImageRepositoryQuerydsl {

    List<RestaurantImageReadResponse> getRestaurantImageByRestaurantId(Restaurant restaurant);
}
