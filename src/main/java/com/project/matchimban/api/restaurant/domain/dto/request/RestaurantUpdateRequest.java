package com.project.matchimban.api.restaurant.domain.dto.request;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.common.global.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Schema(description = "매장 수정 Request")
public class RestaurantUpdateRequest {

    @Schema(description = "매장 ID")
    @NotNull(message = "매장 ID를 등록해주세요.")
    private Long id;

    @Schema(description = "매장 오너 ID")
    @NotNull(message = "매장 오너 ID를 등록해주세요.")
    private Long userId;

    @Schema(description = "매장 카테고리")
    private RestaurantCategory category;

    @Schema(description = "상호명")
    private String name;

    @Schema(description = "사업자번호")
    private String businessNumber;

    @Schema(description = "매장 소개")
    private String introduction;

    @Schema(description = "매장 전화번호")
    private String telephone;

    @Schema(description = "영업 시간")
    private String businessHours;

    @Schema(description = "휴무일")
    private String closedDays;

    @Schema(description = "매장 공지")
    private String notice;

    @Schema(description = "주소-시/도")
    private String addrSido;

    @Schema(description = "주소-시/군/구")
    private String addrSigg;

    @Schema(description = "주소-읍/면/동")
    private String addrEmd;

    @Schema(description = "상세 주소")
    private String addrDetail;

    @Schema(description = "위도")
    private double latitude;

    @Schema(description = "경도")
    private double longitude;

    @Schema(description = "원산지")
    private String originCountry;

    @Schema(description = "매장 상태")
    private RestaurantStatus status;
}
