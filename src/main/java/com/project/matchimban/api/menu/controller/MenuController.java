package com.project.matchimban.api.menu.controller;

import com.project.matchimban.api.menu.dto.dto.request.MenuCreateRequest;
import com.project.matchimban.api.menu.dto.dto.response.MenusReadResponse;
import com.project.matchimban.api.menu.service.MenuService;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.common.exception.ValidResult;
import com.project.matchimban.common.response.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Menu", description = "메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants/{restaurantId}/menus")
public class MenuController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Operation(summary = "메뉴 등록", description = "메뉴 정보를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "등록 성공")
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

    @Operation(summary = "메뉴 전체 조회", description = "메뉴 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "조회 성공", content = @Content(schema = @Schema(implementation = MenusReadResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<Object> getMenus(
            @Parameter(description = "매장 id 값을 받는 변수")
            @PathVariable Long restaurantId
    ) {
        Restaurant restaurant = restaurantService.validateRestaurantId(restaurantId);
        List<MenusReadResponse> menus = menuService.getMenus(restaurant);
        ResultData result = new ResultData();
        result.setResult(menus);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
