package com.project.matchimban.api.menu.dto.dto.response;

import com.project.matchimban.api.menu.dto.entity.Menu;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "메뉴 조회 DTO")
public class MenusReadResponse {
    @Schema(description = "메뉴 ID")
    private Long id;

    @Schema(description = "메뉴 이름")
    private String name;

    @Schema(description = "메뉴 가격")
    private int price;

    @Schema(description = "메뉴 이미지")
    private String imageUrl;

    @QueryProjection
    public MenusReadResponse(Menu menu, String savedFileUrl) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.imageUrl = savedFileUrl;
    }
}
