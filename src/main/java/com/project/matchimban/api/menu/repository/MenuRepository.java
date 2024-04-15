package com.project.matchimban.api.menu.repository;

import com.project.matchimban.api.menu.domain.entity.Menu;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.restaurant = :restaurant")
    List<Menu> findByRestaurantId(@Param("restaurant") Restaurant restaurant);
}
