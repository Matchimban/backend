package com.project.matchimban.api.restaurant.domain.dto.response;

import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import com.project.matchimban.common.global.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "매장 상세 조회 DTO")
public class RestaurantReadResponse {

    private Long id;

    private Long userId;

    private RestaurantCategory category;

    private String name;

    private String businessNumber;

    private String introduction;
    private String telephone;
    private String businessHours;
    private String closedDays;
    private String notice;

    private Address address;

    private String originCountry;

    private RestaurantStatus status;

    public static RestaurantReadResponse createRestaurantDetailReadResponse(Restaurant restaurant) {
        return RestaurantReadResponse.builder()
                .id(restaurant.getId())
                .userId(restaurant.getUser().getId())
                .category(restaurant.getCategory())
                .name(restaurant.getName())
                .businessNumber(restaurant.getBusinessNumber())
                .introduction(restaurant.getIntroduction())
                .telephone(restaurant.getTelephone())
                .businessHours(restaurant.getBusinessHours())
                .closedDays(restaurant.getClosedDays())
                .notice(restaurant.getNotice())
                .address(restaurant.getAddress())
                .originCountry(restaurant.getOriginCountry())
                .status(restaurant.getStatus())
                .build();
    }
}
