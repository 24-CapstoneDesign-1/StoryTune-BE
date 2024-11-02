package com.capstone.storytune.domain.user.util;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class SecretKeyFactory {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public SecretKey create(){
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
