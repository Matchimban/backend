package com.project.matchimban.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.matchimban.api.auth.jwt.JwtProvider;
import com.project.matchimban.api.auth.security.filter.JwtAuthenticationFilter;
import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.response.ResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // Http basic Auth 기반으로 로그인 인증창이 뜸. 기본 인증 로그인을 이용하지 않으면 disable.
                .csrf().disable() // 브라우저를 사용하지 않는 클라이언트(RESTful API)이므로 disable
                // CORS 설정
                .cors(o -> {
                            CorsConfigurationSource source = request -> {
                                // CORS 허용 패턴
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(List.of("*")); // 모든 출처에 대해 허용
                                config.setAllowedMethods(List.of("*")); // 모든 HTTP 메소드에 대해 허용
                                return config; // 해당 설정이 담긴 Cors 구성 객체를 반환
                            };
                            o.configurationSource(source);
                        }
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션정책 : 시큐리티가 세션을 생성하지도 않고, 기존 것을 사용하지도 않음 - JWT토큰방식
                .and()
                .authorizeHttpRequests() // authorizeRequest는 deprecated로 권장되지 않음
                .requestMatchers(
                        new AntPathRequestMatcher("/api/user/signup"),
                        new AntPathRequestMatcher("/api/user/login")
                ).permitAll() // antMatchers 역시 마찬가지
                .requestMatchers(
                        new AntPathRequestMatcher("/api/**")
                ).hasRole("USER")
                .requestMatchers(
                        new AntPathRequestMatcher("/admin/**")
                ).hasRole("ADMIN") // hasRole은 ROLE_이라는 prefix를 자동으로 붙여줌
                .anyRequest().denyAll()
                .and()
                // JWT 인증 필터 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                // 예외 핸들링
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        // 권한 관련 문제
                        // 응답 형식의 통일성을 위해 ResultData에 인증 관련 오류 메시지를 담고
                        ResultData result = new ResultData();
                        result.setCode(HttpStatus.FORBIDDEN.value());
                        result.setMsg(ErrorConstant.ACCESS_DENY);

                        // response를 통해 JSON응답 메시지를 응답
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        objectMapper.writeValue(response.getWriter(), result);
                    }
                })
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        // 인증 관련 문제
                        ResultData result = new ResultData();
                        result.setCode(HttpStatus.UNAUTHORIZED.value());
                        result.setMsg(ErrorConstant.INVALID_TOKEN);
                        
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        objectMapper.writeValue(response.getWriter(), result);
                    }
                });
        return http.build();
    }
}
