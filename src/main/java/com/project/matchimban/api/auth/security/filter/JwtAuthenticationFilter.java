package com.project.matchimban.api.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.matchimban.api.auth.jwt.JwtProvider;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.GlobalExceptionHandler;
import com.project.matchimban.common.response.ResultData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String token = request.getHeader("Authorization");
            if (token != null && jwtProvider.validateToken(token)) {
                token = token.split(" ")[1].trim();
                Authentication auth = jwtProvider.getAuthenticationByToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            ResultData result = new ResultData();
            if (e instanceof MalformedJwtException) {
                result = GlobalExceptionHandler.mapper.convertValue(GlobalExceptionHandler.exceptionMap.get(ErrorConstant.INVALID_TOKEN), ResultData.class);
            } else if (e instanceof SignatureException) {
                result = GlobalExceptionHandler.mapper.convertValue(GlobalExceptionHandler.exceptionMap.get(ErrorConstant.INVALID_SIGNATURE), ResultData.class);
            } else if (e instanceof ExpiredJwtException) {
                result = GlobalExceptionHandler.mapper.convertValue(GlobalExceptionHandler.exceptionMap.get(ErrorConstant.EXPIRED_TOKEN), ResultData.class);
            }

            // response를 통해 JSON응답 메시지를 응답
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), result); //objectMapper : jackson 라이브러리로 Java 객체와 JSON 직렬화/역직렬화 지원
            return; // filter 종료
        }
        filterChain.doFilter(request, response);
    }
}
