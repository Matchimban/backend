package com.project.matchimban.api.user.service;

import com.project.matchimban.api.user.domain.dto.UserSignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Object> signup(UserSignupRequest req);
}
