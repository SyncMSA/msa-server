package com.syncfitgatewayservice.validator;

import com.syncfitcommoncore.dto.AccessTokenDto;
import com.syncfitcommoncore.entity.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access-token-secret}")
    private String accessToken;

    public AccessTokenDto retrieveAccessToken(String accessTokenValue) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .requireIssuer(issuer)
                    .setSigningKey(Keys.hmacShaKeyFor(accessToken.getBytes()))
                    .build()
                    .parseClaimsJws(accessTokenValue);

            return new AccessTokenDto(
                    Long.parseLong(claims.getBody().getSubject()),
                    MemberRole.valueOf(claims.getBody().get("role", String.class)),
                    accessTokenValue);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtException("JWT validation failed", e);
        }
    }
}