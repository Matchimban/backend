package com.project.matchimban.api.auth.jwt.repository;

import com.project.matchimban.api.auth.jwt.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findById(Long userId);
}
