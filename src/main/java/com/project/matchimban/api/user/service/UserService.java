package com.project.matchimban.api.user.service;

import com.project.matchimban.api.auth.jwt.domain.dto.TokenDTO;
import com.project.matchimban.api.user.domain.dto.UserLoginRequest;
import com.project.matchimban.api.user.domain.dto.UserSignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Object> signup(UserSignupRequest req);

    ResponseEntity<Object> login(UserLoginRequest req);

    ResponseEntity<Object> logout(Long userId, String email);

    ResponseEntity<Object> refreshAllTokens(TokenDTO tokens);

}
