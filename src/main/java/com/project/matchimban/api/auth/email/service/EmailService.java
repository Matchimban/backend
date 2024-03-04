package com.project.matchimban.api.auth.email.service;

import com.project.matchimban.api.auth.email.domain.dto.EmailContentsDTO;
import com.project.matchimban.api.auth.email.domain.dto.EmailRequest;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    void sendEmail(String email, EmailContentsDTO emailContentsDTO);

    String createAuthCode(String email);

    ResponseEntity<Object> isAuthCodeValid(EmailRequest req);
}
