package com.iesfranciscodelosrios.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    private String secretKey = "DwldVu8KtBLimNdX055vXxW7wbfAYfocmcpaglFewctC/0v5E+ODcXi/huRrPeil2r7x9GXfxhxAi9Zw+o39wg==";

    private String timeExpiration = "86400000";

    /**
     * Generates an access token for the given email.
     *
     * @param email The email for which the token is generated.
     * @return The generated access token.
     */
    public String generateTokenAccess(String email) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSeignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the access token.
     *
     * @param token The access token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateTokenAccess(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSeignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (Exception e) {
            log.error("Token invalido, error: ".concat(e.getMessage()));
            return false;

        }
    }

    /**
     * Extracts all claims from the provided token.
     *
     * @param token The token from which to extract the claims.
     * @return All claims extracted from the token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSeignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the email from the provided token.
     *
     * @param token The token from which to retrieve the email.
     * @return The email extracted from the token.
     */
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Retrieves a specific claim from the provided token.
     *
     * @param token            The token from which to retrieve the claim.
     * @param claimsTFunction  The function to apply to extract the specific claim.
     * @param <T>              The type of the claim value.
     * @return The value of the specific claim extracted from the token.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /**
     * Generates the signature key using the secret key.
     *
     * @return The generated signature key.
     */
    public Key getSeignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
