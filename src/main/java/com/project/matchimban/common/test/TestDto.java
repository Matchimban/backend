package com.project.matchimban.common.test;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TestDto {
    @NotBlank(message = "입력이 필요합니다.")
    private String name;


    @NotNull(message = "입력이 필요합니다.")
    @Min(value = 3, message = "3 이상 값 이어야 합니다.")
    private Integer num;
}
