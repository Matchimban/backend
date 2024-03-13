package com.project.matchimban.api.restaurant.repository.impl;

import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.repository.RestaurantRepositoryQuerydsl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryQuerydslImpl implements RestaurantRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public List<RestaurantReadResponse> getRestaurantsLeftJoinImage() {
        return new ArrayList<>();
    }
}