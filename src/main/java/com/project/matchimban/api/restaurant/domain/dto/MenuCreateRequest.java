package com.project.matchimban.api.restaurant.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Schema(description = "메뉴 등록 DTO")
public class MenuCreateRequest {

    private String name;

    private int price;

    private MultipartFile image;
}
