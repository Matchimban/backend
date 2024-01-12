package com.project.matchimban.api.user.service.impl;

import com.project.matchimban.api.auth.jwt.JwtProvider;
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
        result.setResult(jwtProvider.createToken(foundUser.getEmail(), foundUser.getUserRole()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
