package com.project.matchimban.api.restaurant.domain.dto.request;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Schema(description = "매장 등록 Request")
public class RestaurantCreateRequest {

    @Schema(description = "카테고리")
    @NotNull(message = "카테고리를 선택해주세요.")
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
    @NotNull(message = "위도를 입력해주세요.")
    private double latitude;

    @Schema(description = "경도")
    @NotNull(message = "경도를 입력해주세요.")
    private double longitude;

    @Schema(description = "매장 소개")
    private String introduction;

    @Schema(description = "핸드폰")
    private String telephone;

    @Schema(description = "영업 시간")
    private String businessHours;

    @Schema(description = "정기 휴무")
    private String closedDays;

    @Schema(description = "안내 및 유의사항")
    private String notice;

    @Schema(description = "사진 리스트 / 사진 명칭에는 반드시 확장자가 포함되어야 합니다")
    @NotNull(message = "사진을 등록해주세요.")
    private List<MultipartFile> images;
}