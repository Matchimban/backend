package com.project.matchimban.api.user.service.impl;

import com.project.matchimban.api.user.domain.dto.UserSignupRequest;
import com.project.matchimban.api.user.domain.entity.User;
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

    @Override
    @Transactional
    public ResponseEntity<Object> signup(UserSignupRequest req) {
        User user = User.signup(req, passwordEncoder.encode(req.getPassword()));
        if (userRepository.existsUserByEmail(req.getEmail())) {
            throw new SVCException(ErrorConstant.DUPLICATED_EMAIL);
        }
        ResultData result = new ResultData();
        result.setResult(userRepository.save(user).getId());
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
