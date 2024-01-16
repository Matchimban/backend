package com.project.matchimban.api.user.controller;

import com.project.matchimban.api.auth.jwt.domain.dto.TokenDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "user", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API", responses = {
            @ApiResponse(responseCode = "20000", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "40001", description = "입력값 유효성 검증 실패", content = @Content(schema = @Schema(implementation = ValidResult.class))),
            @ApiResponse(responseCode = "60000", description = "이미 존재하는 회원", content = @Content(schema = @Schema(implementation = ValidResult.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid UserSignupRequest req) {
        return userService.signup(req);
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginRequest req) {
        return userService.login(req);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshAllTokens(@RequestBody TokenDTO tokens) {
        return userService.refreshAllTokens(tokens);
    }
}
