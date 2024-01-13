package com.project.matchimban.api.auth.jwt;

import com.project.matchimban.api.auth.security.service.CustomUserDetailsService;
import com.project.matchimban.api.user.domain.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final long exp = 1000L * 60 * 60; // 1Hour

    @Value("${jwt.secret.key}")
    private String salt;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
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
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthenticationByToken(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getEmailByToken(token));
        return new UsernamePasswordAuthenticationToken(token, "", userDetails.getAuthorities());
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
        return !claims.getBody().getExpiration().before(new Date());
    }

}
