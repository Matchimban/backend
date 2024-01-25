package com.project.matchimban.api.user.service.impl;

import com.project.matchimban.api.auth.jwt.JwtProvider;
import com.project.matchimban.api.auth.jwt.domain.dto.TokenDTO;
import com.project.matchimban.api.auth.jwt.domain.entity.RefreshToken;
import com.project.matchimban.api.user.domain.dto.UserLoginRequest;
import com.project.matchimban.api.user.domain.dto.UserSignupRequest;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.domain.enums.UserStatus;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.api.user.service.UserService;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public ResponseEntity<Object> signup(UserSignupRequest req) {
        User user = User.signup(req, passwordEncoder.encode(req.getPassword()));
        if (userRepository.existsUserByEmail(req.getEmail())) {
            throw new SVCException(ErrorConstant.DUPLICATED_EMAIL);
        }
        ResultData result = new ResultData();
        result.setResult(userRepository.save(user).getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> login(UserLoginRequest req) {
        User foundUser = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));

        if (!passwordEncoder.matches(req.getPassword(), foundUser.getPassword())) {
            throw new SVCException(ErrorConstant.PASSWORD_MISMATCH);
        }

        if (!UserStatus.ACTIVE.name().equals(foundUser.getStatus().name())) {
            throw new SVCException(ErrorConstant.NOT_ACTIVE_USER);
        }

        ResultData result = new ResultData();
        result.setResult(TokenDTO.builder()
                        .accessToken(jwtProvider.createAccessToken(foundUser.getEmail(), foundUser.getUserRole()))
                        .refreshToken(jwtProvider.createRefreshToken(foundUser.getId()))
                        .build());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> logout(Long userId, String email) {
        // Access Token 삭제, Refresh Token 삭제
        jwtProvider.deleteAccessTokenByEmail(email);
        jwtProvider.deleteRefreshTokenByUserId(userId);
        ResultData result = new ResultData();
        result.setMsg("로그아웃 성공");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> refreshAllTokens(TokenDTO tokens) {
        // Access Token 검증
        String email = jwtProvider.getEmailByToken(tokens.getAccessToken());
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_USER));

        // 찾은 유저 정보를 기반으로 Refresh Token 조회
        RefreshToken foundRefreshToken = jwtProvider.getRefreshTokenByUserId(foundUser.getId())
                .orElseThrow(() -> new SVCException(ErrorConstant.NOT_FOUND_TOKEN));

        // 유저 정보로 가져온 Refresh Token과 전달받은 Refresh Token 일치 여부 확인
        if (foundRefreshToken.getRefreshToken() == null || !foundRefreshToken.getRefreshToken().equals(tokens.getRefreshToken())) {
            throw new SVCException(ErrorConstant.INVALID_REFRESH_TOKEN);
        }

        // Redis에서 기존 Refresh Token 삭제
        jwtProvider.deleteRefreshTokenByUserId(foundRefreshToken.getId());

        ResultData result = new ResultData();
        result.setResult(TokenDTO.builder()
                .accessToken(jwtProvider.createAccessToken(foundUser.getEmail(), foundUser.getUserRole()))
                .refreshToken(jwtProvider.createRefreshToken(foundUser.getId()))
                .build());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
