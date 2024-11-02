package com.capstone.storytune.domain.user.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final SecretKeyFactory secretKeyFactory;

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000 * 14L;

    public String generateAccessToken(Long memberId) {
        val authentication = UserAuthentication.create(memberId);
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(Long memberId) {
        val authentication = UserAuthentication.create(memberId);
        return generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(Authentication authentication, Long expirationTime) {
        val now = new Date();

        val claims = Jwts.claims()
                .setSubject(authentication.getPrincipal().toString()) // memberId를 subject로 설정
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(secretKeyFactory.create())
                .compact();
    }
}
