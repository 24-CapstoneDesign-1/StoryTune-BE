package com.capstone.storytune.domain.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final SecretKeyFactory secretKeyFactory;

    public JwtValidationType validateToken(String token){
        try{
            getBody(token);
            return JwtValidationType.VALID_JWT;
        }catch (RuntimeException exception){
            return JwtValidationType.INVALID_JWT;
        }
    }

    public Long getUserFromJwt(String token){
        val claims = getBody(token);
        return Long.parseLong(claims.get("memberId").toString());
    }

    private Claims getBody(final String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyFactory.create())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
