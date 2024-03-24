package com.project.matchimban.api.menu.domain.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "메뉴 조회 이미지 정보 DTO")
@EqualsAndHashCode(of = "savedFileUrl")
public class MenuImagesReadResponse {
    private Long imageId;
    private String savedFileUrl;

    @QueryProjection
    public MenuImagesReadResponse(Long imageId, String savedFileUrl) {
        this.imageId = imageId;
        this.savedFileUrl = savedFileUrl;
    }
}
