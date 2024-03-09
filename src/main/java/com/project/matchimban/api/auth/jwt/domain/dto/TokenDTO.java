package com.project.matchimban.api.auth.jwt.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(description = "Token 응답 DTO")
public class TokenDTO {

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "Refresh Token")
    private String refreshToken;

    @Schema(description = "Access Token 생성 시간")
    private Date generatedTime;

    @Schema(description = "인증된 유저 정보")
    private UserInfo userInfo;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Schema(description = "인증된 회원 정보")
    public static class UserInfo {
        @Schema(description = "회원 고유 ID")
        private long userId;
        @Schema(description = "이메일")
        private String email;
        @Schema(description = "이름")
        private String name;
        @Schema(description = "닉네임")
        private String nickname;
        @Schema(description = "연락처")
        private String phone;
        @Schema(description = "권한")
        private String role;
        @Schema(description = "계정 상태")
        private String status;
    }
}
