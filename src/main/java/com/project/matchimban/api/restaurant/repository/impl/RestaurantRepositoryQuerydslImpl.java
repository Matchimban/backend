package com.project.matchimban.api.restaurant.repository.impl;

import com.project.matchimban.api.restaurant.domain.dto.response.QRestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.QRestaurant;
import com.project.matchimban.api.restaurant.domain.entity.QRestaurantImage;
import com.project.matchimban.api.restaurant.repository.RestaurantRepositoryQuerydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.project.matchimban.api.restaurant.domain.entity.QRestaurant.restaurant;
import static com.project.matchimban.api.restaurant.domain.entity.QRestaurantImage.restaurantImage;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryQuerydslImpl implements RestaurantRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public List<RestaurantReadResponse> getRestaurantsLeftJoinImage() {
        return queryFactory
                .select(new QRestaurantReadResponse(restaurant, restaurantImage.savedFileUrl))
                .from(restaurant)
                .leftJoin(restaurant.restaurantImages, restaurantImage)
                .fetch();
    }
}