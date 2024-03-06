package com.project.matchimban.api.restaurant.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "메뉴 등록 DTO")
public class MenuCreateRequest {

    @Schema(description = "메뉴 이름")
    @NotBlank(message = "메뉴 이름을 등록해주세요")
    private String name;

    @Schema(description = "가격")
    @NotNull(message = "가격을 등록해주세요")
    private int price;

    @Schema(description = "이미지")
    @NotNull(message = "메뉴 이미지 파일을 등록해주세요")
    private MultipartFile image;
}
