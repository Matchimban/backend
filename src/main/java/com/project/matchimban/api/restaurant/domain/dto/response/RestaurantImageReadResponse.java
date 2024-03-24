package com.project.matchimban.api.restaurant.domain.dto.response;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantImageCategory;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "매장 이미지 조회 DTO")
public class RestaurantImageReadResponse {

    @Schema(name = "매장 이미지 id")
    private Long id;

    @Schema(name = "매장 이미지 타입", description = "MAIN 혹은 SUB로 나타남.")
    private RestaurantImageCategory category;

    @Schema(name = "매장 이미지 URL")
    private String imageUrl;

    @QueryProjection
    public RestaurantImageReadResponse(Long id, RestaurantImageCategory category, String imageUrl) {
        this.id = id;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
