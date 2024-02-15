package com.project.matchimban.api.user.controller;

import com.project.matchimban.api.auth.jwt.domain.dto.TokenDTO;
import com.project.matchimban.api.auth.security.model.CurrentUser;
import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.user.domain.dto.UserLoginRequest;
import com.project.matchimban.api.user.domain.dto.UserSignupRequest;
import com.project.matchimban.api.user.service.UserService;
import com.project.matchimban.common.exception.ValidResult;
import com.project.matchimban.common.response.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@Tag(name = "user", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API", responses = {
            @ApiResponse(responseCode = "20000", description = "[성공] 회원가입 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "40001", description = "[실패] 입력값 유효성 검증 실패", content = @Content(schema = @Schema(implementation = ValidResult.class))),
            @ApiResponse(responseCode = "60000", description = "[실패] 이미 존재하는 이메일입니다.")
    })
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid UserSignupRequest req) {
        return userService.signup(req);
    }


    @Operation(summary = "로그인 API", responses = {
            @ApiResponse(responseCode = "20000", description = "[성공] 로그인 완료", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
            @ApiResponse(responseCode = "40001", description = "[실패] 입력값 유효성 검증 실패", content = @Content(schema = @Schema(implementation = ValidResult.class))),
            @ApiResponse(responseCode = "60001", description = "[실패] 존재하지 않는 회원입니다."),
            @ApiResponse(responseCode = "60004", description = "[실패] 패스워드가 일치하지 않습니다."),
            @ApiResponse(responseCode = "60005", description = "[실패] 계정이 정지 또는 비활성화 되었습니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginRequest req) {
        return userService.login(req);
    }


    @Operation(summary = "로그아웃 API", description = "📌 Request Body 없이 액세스 토큰만 전달하면 됩니다.", responses = {
            @ApiResponse(responseCode = "20000", description = "[성공] 로그아웃 완료")
    })
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout(@CurrentUser CustomUserDetails currentUser) {
        return userService.logout(currentUser.getUserId(), currentUser.getUsername());
    }


    @Operation(summary = "Refresh Token 재발급 API", responses = {
            @ApiResponse(responseCode = "20000", description = "[성공] Refresh Token 재발급 완료", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
            @ApiResponse(responseCode = "60001", description = "[실패] 존재하지 않는 회원입니다."),
            @ApiResponse(responseCode = "60002", description = "[실패] Access Token 형식이 잘못되었습니다."),
            @ApiResponse(responseCode = "60006", description = "[실패] Refresh Token이 만료되었습니다. 로그인을 다시 하세요."),
            @ApiResponse(responseCode = "60007", description = "[실패] 유효하지 않은 Refresh Token입니다.")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshAllTokens(@RequestBody TokenDTO tokens) {
        return userService.refreshAllTokens(tokens);
    }
}
