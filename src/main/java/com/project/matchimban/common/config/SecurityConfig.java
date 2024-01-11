package com.project.matchimban.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/register")
                ).permitAll() // antMatchers 역시 마찬가지
                .requestMatchers(
                        new AntPathRequestMatcher("/admin/**")
                ).hasRole("ADMIN") // hasRole은 ROLE_이라는 prefix를 자동으로 붙여줌
                .anyRequest().denyAll();

        return http.build();
    }
}
