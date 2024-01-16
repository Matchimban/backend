package com.project.matchimban.api.auth.jwt.domain.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
