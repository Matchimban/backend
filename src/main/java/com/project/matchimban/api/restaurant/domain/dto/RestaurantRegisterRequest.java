package com.project.matchimban.api.restaurant.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Schema(description = "매장 등록 Request")
public class RestaurantRegisterRequest {

    @Schema(description = "매장 정보")
    @NotNull(message = "매장 정보를 등록해주세요.")
    private RestaurantCreateRequest restaurant;

    @Schema(description = "매장 이미지 정보")
    @NotNull(message = "매장 이미지 정보를 등록해주세요.")
    private List<RestaurantImageCreateRequest> images;

    @Schema(description = "메뉴 정보")
    @NotNull(message = "메뉴 정보를 등록해주세요.")
    private List<MenuCreateRequest> menus;
}