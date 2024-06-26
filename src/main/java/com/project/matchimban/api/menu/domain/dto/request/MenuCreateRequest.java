package com.project.matchimban.api.menu.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "메뉴 등록 DTO")
public class MenuCreateRequest {

    @Schema(description = "메뉴 이름")
    @NotBlank(message = "메뉴 이름을 등록해주세요")
    private String name;

    @Schema(description = "메뉴 가격")
    @NotNull(message = "메뉴 가격을 등록해주세요")
    private int price;

    @Schema(description = "메뉴 이미지")
    private List<MultipartFile> images;
}
