package com.project.matchimban.api.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "로그인 요청 DTO")
@Getter
public class UserLoginRequest {
    @Schema(description = "이메일")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

    @Schema(description = "패스워드")
    @NotBlank(message = "패스워드를 입력해 주세요.")
    private String password;
}
