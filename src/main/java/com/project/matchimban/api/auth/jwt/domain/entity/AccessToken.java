package com.project.matchimban.api.auth.jwt.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@RedisHash(value = "access")
public class AccessToken {

    @Id
    private String email;

    @Indexed
    private String accessToken;

    @TimeToLive(unit = TimeUnit.HOURS)
    private Integer ttl;

}
