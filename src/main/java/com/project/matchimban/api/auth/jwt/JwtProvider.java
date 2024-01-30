package com.project.matchimban.api.auth.jwt;

import com.project.matchimban.api.auth.jwt.domain.entity.AccessToken;
import com.project.matchimban.api.auth.jwt.domain.entity.RefreshToken;
import com.project.matchimban.api.auth.jwt.repository.AccessTokenRepository;
import com.project.matchimban.api.auth.jwt.repository.RefreshTokenRepository;
import com.project.matchimban.api.auth.security.service.CustomUserDetailsService;
import com.project.matchimban.api.user.domain.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final long exp = 1000L * 60 * 60; // 1Hour
    private final Integer ttl = 7; // 7Days

    @Value("${jwt.secret.key}")
    private String salt;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String email, UserRole userRole) {
        AccessToken savedAccessToken = accessTokenRepository.save(
                AccessToken.builder()
                        .email(email)
                        .accessToken(createToken(email, userRole))
                        .ttl(1)
                        .build()
        );
        return savedAccessToken.getAccessToken();
    }

    public String createRefreshToken(Long userId) {
        // 만약 Refresh Token도 JWT형식으로 발급할 거면
        // createAccessToken() 메소드와 동일한 형태를 가지되, redis에 save하는 로직만 추가하면 된다.
        RefreshToken savedRefreshToken = refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(userId)
                        .refreshToken(UUID.randomUUID().toString())
                        .ttl(ttl)
                        .build()
        );
        return savedRefreshToken.getRefreshToken();
    }

    public String createToken(String email, UserRole userRole) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", userRole.name());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailByToken(String token) {
        String email = "";
        try {
            email = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
        return email;
    }

    public Optional<AccessToken> getAccessTokenByEmail(String email) {
        return accessTokenRepository.findById(email);
    }

    public Optional<RefreshToken> getRefreshTokenByUserId(Long userId) {
        return refreshTokenRepository.findById(userId);
    }

    public void deleteAccessTokenByEmail(String email) {
        accessTokenRepository.deleteById(email);
    }

    public void deleteRefreshTokenByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }

    public boolean validateToken(String token) {
        if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
            return false;
        } else {
            token = token.split(" ")[1].trim();
        }

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        // 로그아웃된 Access Token인지 검증
        String email = claims.getBody().getSubject();
        Optional<AccessToken> foundAccessToken = getAccessTokenByEmail(email);
        if (foundAccessToken.isEmpty()) {
            return false;
        }

        return !claims.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthenticationByToken(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getEmailByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
