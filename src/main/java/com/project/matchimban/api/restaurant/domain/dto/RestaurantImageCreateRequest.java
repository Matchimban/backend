package com.project.matchimban.api.restaurant.domain.dto;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantImageCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "매장 이미지 등록 DTO")
public class RestaurantImageCreateRequest {
    @Schema(description = "이미지 유형")
    @NotNull(message = "이미지 유형을 선택해주세요")
    private RestaurantImageCategory category;

    @Schema(description = "이미지 파일")
    @NotNull(message = "이미지 파일을 등록해주세요")
    private MultipartFile file;
}
