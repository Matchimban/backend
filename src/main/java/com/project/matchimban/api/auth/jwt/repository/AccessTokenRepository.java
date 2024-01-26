package com.project.matchimban.api.auth.jwt.repository;

import com.project.matchimban.api.auth.jwt.domain.entity.AccessToken;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
}
