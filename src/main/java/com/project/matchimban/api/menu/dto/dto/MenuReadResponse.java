package com.project.matchimban.api.menu.dto.dto;

import com.project.matchimban.api.menu.dto.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "메뉴 조회 DTO")
public class MenuReadResponse {
    @Schema(description = "메뉴 ID")
    private Long id;

    @Schema(description = "메뉴 이름")
    private String name;

    @Schema(description = "메뉴 가격")
    private int price;

    @Schema(description = "메뉴 이미지")
    private String imageUrl;

    public static MenuReadResponse createMenuReadResponse(Menu menu, String menuImage) {
        return MenuReadResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .imageUrl(menuImage)
                .build();
    }
}
