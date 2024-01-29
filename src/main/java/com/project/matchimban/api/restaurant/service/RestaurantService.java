package com.project.matchimban.api.restaurant.service;

import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateRequest dto);
}
