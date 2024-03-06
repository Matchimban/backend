package com.project.matchimban.api.auth.email.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "authcode")
public class EmailAuthCode {

    @Id
    private String email;

    @Indexed
    private String authCode;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Integer ttl;

}
