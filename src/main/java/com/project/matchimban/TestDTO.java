package com.project.matchimban;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
//@Setter
@Schema(description = "testDTO 스키마")
public class TestDTO {
    @Schema(description = "이름")
    private String name;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "나이")
    private int age;

    @Schema(description = "주소")
    private String address;
}
