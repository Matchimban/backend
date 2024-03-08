package com.project.matchimban.common.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원정보 응답 DTO")
public class TestRes {
    @Schema(description = "이름")
    private String name;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "나이")
    private int age;

    @Schema(description = "주소")
    private String address;
}
