package com.project.matchimban.api.auth.jwt.repository;

import com.project.matchimban.api.auth.jwt.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
