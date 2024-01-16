package com.project.matchimban.api.auth.jwt.domain.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@RedisHash(value = "refresh")
public class RefreshToken {

    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.DAYS)
    private Integer ttl;

    public void setTTL(Integer ttl) {
        this.ttl = ttl;
    }
}
