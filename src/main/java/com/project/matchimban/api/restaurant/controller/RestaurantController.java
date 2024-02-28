package com.project.matchimban.api.restaurant.controller;

import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.RestaurantRegisterRequest;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.common.response.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Restaurant", description = "매장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Operation(summary = "매장 등록", description = "매장, 매장 이미지, 메뉴를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "등록 성공"),
            @ApiResponse(responseCode = "40001", description = "입력값 유효성 검증 실패"),
            @ApiResponse(responseCode = "60000", description = "이미 존재하는 회원")
    })
    @PostMapping("")
    public ResponseEntity<Object> registerRestaurant(
            @ModelAttribute @Valid RestaurantRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        restaurantService.registerRestaurant(request, userDetails);
        return new ResponseEntity<>(new ResultData(), HttpStatus.CREATED);
    }

//    @PostMapping("")
//    public ResponseEntity<Object> registerRestaurant(
//            @RequestBody @Valid RestaurantCreateRequest dto,
//            @AuthenticationPrincipal CustomUserDetails userDetails
//    ) {
//        restaurantService.registerRestaurant(dto, userDetails);
//        return new ResponseEntity<>(new ResultData(), HttpStatus.CREATED);
//    }

//    @PostMapping("/{restaurantId}/image")
//    public ResponseEntity<Object> registerRestaurantImage(
//            @PathVariable Long restaurantId,
//            @RequestBody @Valid List<RestaurantImageCreateRequest> request) {
//        restaurantService.registerRestaurantImage(restaurantId, images);
//        return new ResponseEntity<>(new ResultData(), HttpStatus.CREATED);
//    }
}
