package com.project.matchimban.api.menu.controller;

import com.project.matchimban.api.menu.dto.dto.MenuCreateRequest;
import com.project.matchimban.api.menu.service.MenuService;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.common.response.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Menu", description = "메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants/{restaurantId}/menus")
public class MenuController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Operation(summary = "메뉴 등록 및 수정", description = "메뉴 정보를 등록 및 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "등록 및 수정 성공")
    })
    @PostMapping("")
    public ResponseEntity<Object> createMenu(
            @Parameter(description = "매장 id 값을 받는 변수")
            @PathVariable Long restaurantId,
            @Parameter(description = "메뉴의 정보를 받는 객체")
            @ModelAttribute @Valid MenuCreateRequest request
    ) {
        Restaurant restaurant = restaurantService.validateRestaurantId(restaurantId);
        menuService.createMenu(restaurant, request);
        return new ResponseEntity<>(new ResultData(), HttpStatus.OK);
    }
}
