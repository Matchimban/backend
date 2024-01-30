package com.project.matchimban.api.restaurant.controller;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantImageCreateRequest;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<Object> createRestaurant(
            @RequestBody @Valid RestaurantCreateRequest dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        restaurantService.createRestaurant(dto, userDetails);
        return new ResponseEntity<>(new ResultData(), HttpStatus.CREATED);
    }

    @PostMapping("/{restaurantId}/image")
    public ResponseEntity<Object> registerRestaurantImage(
            @PathVariable Long restaurantId,
            @RequestBody @Valid List<RestaurantImageCreateRequest> images) {
        restaurantService.registerRestaurantImage(restaurantId, images);
        return new ResponseEntity<>(new ResultData(), HttpStatus.CREATED);
    }
}
