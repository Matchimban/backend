package com.project.matchimban.api.auth.email.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;

@Schema(description = "이메일 인증 요청 DTO")
@Getter
public class EmailRequest {

    @Schema(description = "수신자 이메일")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "인증 코드")
    private String authCode;

}