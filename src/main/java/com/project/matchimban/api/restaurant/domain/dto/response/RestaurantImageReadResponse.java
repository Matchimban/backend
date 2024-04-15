package com.project.matchimban.api.restaurant.domain.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "매장 이미지 조회 DTO")
public class RestaurantImageReadResponse {

    @Schema(description = "매장 이미지 id")
    Long id;

    @Schema(description = "매장 id")
    Long restaurantId;

    @Schema(description = "원본 이미지 이름")
    String originFileName;

    @Schema(description = "저장된 이미지 url")
    String savedFileUrl;

    @QueryProjection
    public RestaurantImageReadResponse(Long id, Long restaurantId, String originFileName, String savedFileUrl) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.originFileName = originFileName;
        this.savedFileUrl = savedFileUrl;
    }
}
