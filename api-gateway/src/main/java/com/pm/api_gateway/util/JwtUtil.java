package com.pm.api_gateway.util;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET = "Base64EncodedSecretKeyForJWTSigningExample";
    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
