package com.project.matchimban.api.restaurant.repository.impl;

import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantsReadResponse;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantImageCategory;
import com.project.matchimban.api.restaurant.repository.RestaurantRepositoryQuerydsl;
import com.querydsl.core.types.Projections;
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

    public List<RestaurantsReadResponse> getRestaurantsLeftJoinImage() {
        return queryFactory
                .select(Projections.constructor(RestaurantsReadResponse.class, restaurant, restaurantImage.savedFileUrl))
                .from(restaurant)
                .leftJoin(restaurant.restaurantImages, restaurantImage)
                .where(restaurantImage.imageCategory.eq(RestaurantImageCategory.MAIN))
                .fetch();
    }
}