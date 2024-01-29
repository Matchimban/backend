package com.project.matchimban.api.restaurant.controller;

import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<Object> createRestaurant(@Valid RestaurantCreateRequest dto) {
        restaurantService.createRestaurant(dto);
        return new ResponseEntity<>(new ResultData(), HttpStatus.OK);
    }
}
