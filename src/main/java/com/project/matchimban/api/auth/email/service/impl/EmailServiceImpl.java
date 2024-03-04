package com.project.matchimban.api.auth.email.service.impl;

import com.project.matchimban.api.auth.email.domain.dto.EmailContentsDTO;
import com.project.matchimban.api.auth.email.domain.dto.EmailRequest;
import com.project.matchimban.api.auth.email.domain.entity.EmailAuthCode;
import com.project.matchimban.api.auth.email.repository.EmailAuthRepository;
import com.project.matchimban.api.auth.email.service.EmailService;
import com.project.matchimban.api.user.service.UserService;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final EmailAuthRepository emailAuthRepository;


    @Override
    public void sendEmail(String email, EmailContentsDTO emailContentsDTO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setTo(email);
            messageHelper.setSubject(emailContentsDTO.getSubject());
            messageHelper.setText(emailContentsDTO.getContent());
        } catch (MessagingException e) {
            throw new SVCException(ErrorConstant.FAILED_TO_SEND_EMAIL);
        }

        javaMailSender.send(message);
    }

    @Override
    public String createAuthCode(String email) {
        // 1. rdms에 이메일 중복되었는지 확인
        if ( userService.isEmailDuplicated(email) ) {
            throw new SVCException(ErrorConstant.DUPLICATED_EMAIL);
        }

        // 2. 중복되지 않았다면 랜덤 인증코드 생성 및 email과 함께 redis에 저장
        // 2-1. 인증코드 생성
        int length = 6; // 인증코드 자릿수 지정
        try {
            Random random = SecureRandom.getInstanceStrong(); // 시큐어 난수 생성
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++)
                sb.append(random.nextInt(10)); // 0~9 사이의 난수 생성
            
            // 2-2. 생성된 난수 email 정보와 함께 redis에 저장
            EmailAuthCode emailAuthCode = emailAuthRepository.save(
                    EmailAuthCode.builder()
                            .email(email)
                            .authCode(sb.toString())
                            .ttl(1) // 테스트 1분
                            .build()
            );

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("랜덤코드 생성 시 오류 발생");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> isAuthCodeValid(EmailRequest req) {
        EmailAuthCode foundEmailAuthCode = emailAuthRepository.findById(req.getEmail())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_EMAIL_AND_CODE));

        boolean valid = false;

        if (foundEmailAuthCode.getAuthCode().equals(req.getAuthCode()))
            valid = true;

        ResultData result = new ResultData();
        result.setResult(valid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
