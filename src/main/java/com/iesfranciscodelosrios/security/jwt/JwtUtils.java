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

    // Generar token
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

    // Validar token
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

    // Obtener todos los claims del token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSeignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener email del token
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Obtener un solo Claim del token
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // Obtener firma del token
    public Key getSeignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
