package com.project.matchimban.api.restaurant.domain.dto.response;

import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "매장 전체 조회 DTO")
public class RestaurantsReadResponse {

    @Schema(description = "매장 ID")
    List<RestaurantReadResponse> restaurantReadResponses;
}
