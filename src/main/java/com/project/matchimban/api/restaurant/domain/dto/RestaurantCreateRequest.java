package com.project.matchimban.api.restaurant.domain.dto;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Schema(description = "매장 등록 DTO")
public class RestaurantCreateRequest {

    @Schema(description = "회원 아이디")
    @NotBlank(message = "회원 아이디를 입력해주세요.")
    private Long userId;

    @Schema(description = "카테고리")
    @NotBlank(message = "카테고리를 선택해주세요.")
    private RestaurantCategory category;

    @Schema(description = "상호명")
    @NotBlank(message = "상호명을 입력해주세요.")
    private String name;

    @Schema(description = "사업자 등록번호")
    @NotBlank(message = "사업자 등록번호를 입력해주세요.")
    private String businessNumber;

    @Schema(description = "원산지")
    @NotBlank(message = "원산지를 입력해주세요.")
    private String originCountry;

    @Schema(description = "주소-시/도")
    @NotBlank(message = "주소-시/도를 입력해주세요.")
    private String addrSido;

    @Schema(description = "주소-시/군/구")
    @NotBlank(message = "주소-시/군/구를 입력해주세요.")
    private String addrSigg;

    @Schema(description = "주소-읍/면/동")
    @NotBlank(message = "주소-읍/면/동을 입력해주세요.")
    private String addrEmd;

    @Schema(description = "상세 주소")
    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String addrDetail;

    @Schema(description = "위도")
    @NotBlank(message = "위도를 입력해주세요.")
    private double latitude;

    @Schema(description = "경도")
    @NotBlank(message = "경도를 입력해주세요.")
    private double longitude;
}