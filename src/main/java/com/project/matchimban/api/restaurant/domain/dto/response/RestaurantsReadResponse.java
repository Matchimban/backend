package com.project.matchimban.api.restaurant.domain.dto.response;

import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "매장 전체 조회 세부 DTO")
public class RestaurantsReadResponse {

    @Schema(description = "매장 id")
    private Long id;

    @Schema(description = "메장 카테고리")
    private RestaurantCategory category;

    @Schema(description = "메장 이름")
    private String name;

    @Schema(description = "매장 주소(시/도)")
    private String addrSido;

    @Schema(description = "매장 MAIN 사진")
    private String imageUrl;

    @QueryProjection
    public RestaurantsReadResponse(Restaurant restaurant, String savedFileUrl) {
        this.id = restaurant.getId();
        this.category = restaurant.getCategory();
        this.name = restaurant.getName();
        this.addrSido = restaurant.getAddress().getAddrSido();
        this.imageUrl = savedFileUrl;
    }
}
