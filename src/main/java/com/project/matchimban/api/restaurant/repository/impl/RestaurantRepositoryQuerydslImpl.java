package com.project.matchimban.api.restaurant.repository.impl;

import com.project.matchimban.api.restaurant.domain.dto.RestaurantsReadResponse;
import com.project.matchimban.api.restaurant.repository.RestaurantRepositoryQuerydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.project.matchimban.api.restaurant.domain.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryQuerydslImpl implements RestaurantRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public List<RestaurantsReadResponse> getRestaurantsLeftJoinImage() {
        return new ArrayList<>();
    }
}