package com.project.matchimban.api.restaurant.repository.impl;

import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantImageReadResponse;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.repository.RestaurantImageRepositoryQuerydsl;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.matchimban.api.restaurant.domain.entity.QRestaurantImage.restaurantImage;

@Repository
@RequiredArgsConstructor
public class RestaurantImageRepositoryQuerydslImpl implements RestaurantImageRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public List<RestaurantImageReadResponse> getRestaurantImageByRestaurantId(Restaurant restaurant) {
        return queryFactory
                .select(Projections.constructor(
                        RestaurantImageReadResponse.class,
                        restaurantImage.id,
                        restaurantImage.restaurant.id,
                        restaurantImage.originFileName,
                        restaurantImage.savedFileUrl
                ))
                .from(restaurantImage)
                .where(restaurantImage.restaurant.id.eq(restaurant.getId()))
                .fetch();
    }
}