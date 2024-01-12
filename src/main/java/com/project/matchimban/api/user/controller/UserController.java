package com.project.matchimban.api.user.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
