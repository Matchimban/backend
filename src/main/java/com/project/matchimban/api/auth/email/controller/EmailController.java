package com.project.matchimban.api.auth.email.controller;

import com.project.matchimban.api.auth.email.domain.dto.EmailContentsDTO;
import com.project.matchimban.api.auth.email.domain.dto.EmailRequest;
import com.project.matchimban.api.auth.email.service.EmailService;
import com.project.matchimban.common.exception.ValidResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "email", description = "이메일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "인증코드 이메일 전송 API", description = "📌 authCode는 검증 시에만 담아서 전송하면 됩니다.", responses = {
            @ApiResponse(responseCode = "40001", description = "[실패] 입력값 유효성 검증 실패", content = @Content(schema = @Schema(implementation = ValidResult.class))),
            @ApiResponse(responseCode = "60000", description = "[실패] 이미 존재하는 이메일입니다.")
    })
    @PostMapping("/auth/authentication-code")
    public void sendAuthenticationCodeToEmail(@RequestBody @Valid EmailRequest req) {
        EmailContentsDTO emailContentsDTO = EmailContentsDTO.builder()
                .subject("맛침반 회원가입을 위한 인증코드입니다.")
                .content(emailService.createAuthCode(req.getEmail()))
                .build();
        emailService.sendEmail(req.getEmail(), emailContentsDTO);
    }

    @Operation(summary = "인증코드 검증 API", responses = {
            @ApiResponse(responseCode = "20000", description = "[성공] 결과 반환 - true 일치 / false 불일치", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "40001", description = "[실패] 입력값 유효성 검증 실패", content = @Content(schema = @Schema(implementation = ValidResult.class))),
            @ApiResponse(responseCode = "67002", description = "[실패] 유효시간 만료 또는 이메일 정보가 잘못되었습니다.")
    })
    @PostMapping("/auth/verification-code")
    public ResponseEntity<Object> verifyAuthenticationCode(@RequestBody @Valid EmailRequest req) {
        return emailService.isAuthCodeValid(req);
    }
}
