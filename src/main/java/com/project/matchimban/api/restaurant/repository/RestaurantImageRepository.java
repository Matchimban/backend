package com.project.matchimban.api.restaurant.repository;

import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.entity.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
    // 입력되는 restaurantId로 조회하여 일치하는 image 모두 조회 후 url만 리턴

    @Query("select i.savedFileUrl from RestaurantImage i where i.restaurant = :restaurant order by i.id")
    List<String> findSavedFileUrlByRestaurantId(@Param("restaurant") Restaurant restaurant);
}