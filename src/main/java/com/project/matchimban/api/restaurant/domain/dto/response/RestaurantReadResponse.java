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

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "매장 상세 조회 DTO")
public class RestaurantReadResponse {

    @Schema(description = "매장 ID")
    private Long id;

    @Schema(description = "회원 ID")
    private Long userId;

    @Schema(description = "매장 카테고리")
    private RestaurantCategory category;

    @Schema(description = "매장명")
    private String name;

    @Schema(description = "사업자번호")
    private String businessNumber;

    @Schema(description = "매장 소개")
    private String introduction;

    @Schema(description = "전화 번호")
    private String telephone;

    @Schema(description = "영업 시간")
    private String businessHours;

    @Schema(description = "휴무일")
    private String closedDays;

    @Schema(description = "매장 공지")
    private String notice;

    @Schema(description = "주소")
    private Address address;

    @Schema(description = "원산지")
    private String originCountry;

    @Schema(description = "매장 상태")
    private RestaurantStatus status;

    @Schema(description = "매장 사진")
    @Builder.Default
    private List<RestaurantImageReadResponse> images = new ArrayList<>();

    public static RestaurantReadResponse createRestaurantDetailReadResponse(Restaurant restaurant, List<RestaurantImageReadResponse> images) {
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
                .images(images)
                .build();
    }
}
