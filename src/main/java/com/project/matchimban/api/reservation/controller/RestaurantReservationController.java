package com.project.matchimban.api.reservation.controller;

import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationCreateRequest;
import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationGetResponse;
import com.project.matchimban.api.reservation.domain.dto.RestaurantReservationUpdateRequest;
import com.project.matchimban.api.reservation.service.RestaurantReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "RestaurantReservation", description = "매장_예약 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class RestaurantReservationController {

    private final RestaurantReservationService restaurantReservationService;

    @Operation(summary = "(매장_예약)등록", description = "매장의 예약 시스템을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "등록 성공"),
            @ApiResponse(responseCode = "40000-40001", description = "실패: 입력값 검증 실패한 경우"),
            @ApiResponse(responseCode = "40000-61001", description = "실패: 매장 정보가 없는 경우"),
            @ApiResponse(responseCode = "40000-61002", description = "실패: 이미 등록된 경우")
    })
    @PostMapping("/api/restaurant-reservations")
    public ResponseEntity createRestaurantReservation(@Validated @RequestBody RestaurantReservationCreateRequest dto){
        return restaurantReservationService.createRestaurantReservation(dto);
    }

    @Operation(summary = "(매장_예약)조회", description = "매장의 예약 시스템을 조회합니다.")
    @Parameter(name = "restaurantId", description = "매장ID", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "조회 성공", content = @Content(schema = @Schema(implementation = RestaurantReservationGetResponse.class))),
            @ApiResponse(responseCode = "40000-61003", description = "실패: 등록된 예약 시스템이 없는 경우")
    })
    @GetMapping("/api/restaurant-reservations/{restaurantId}")
    public ResponseEntity getRestaurantReservation(@PathVariable Long restaurantId){
        return restaurantReservationService.getRestaurantReservation(restaurantId);
    }

    @Operation(summary = "(매장_예약)수정", description = "매장의 예약 시스템을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "수정 성공"),
            @ApiResponse(responseCode = "40000-40001", description = "실패: 입력값 검증 실패한 경우"),
            @ApiResponse(responseCode = "40000-61003", description = "실패: 등록된 예약 시스템이 없는 경우")
    })
    @PutMapping("/api/restaurant-reservations")
    public ResponseEntity updateRestaurantReservation(@Validated @RequestBody RestaurantReservationUpdateRequest dto){
        return restaurantReservationService.updateRestaurantReservation(dto);
    }
}
