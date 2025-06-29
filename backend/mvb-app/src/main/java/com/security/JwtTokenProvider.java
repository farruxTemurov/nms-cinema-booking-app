package com.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationInMs;

    private Key key;
    private static final Logger logger = Logger.getLogger(JwtTokenProvider.class.getName());

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.length() < 64) {
            throw new IllegalArgumentException("JWT secret key must be at least 64 characters long for HS512.");
        }
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        var authorities = authentication.getAuthorities()
                .stream()
                .map(role -> {
                    String authority = role.getAuthority();
                    return authority.startsWith("ROLE_") ? authority : "ROLE_" + authority;
                })
                .toList();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)  // Add roles in the token
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public List<String> getRolesFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // "roles" claim expected as List<String>
        return claims.get("roles", List.class);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException ex) {
            logger.warning("Invalid JWT signature or malformed token.");
        } catch (ExpiredJwtException ex) {
            logger.warning("Expired JWT token.");
        } catch (UnsupportedJwtException ex) {
            logger.warning("Unsupported JWT token.");
        } catch (IllegalArgumentException ex) {
            logger.warning("JWT claims string is empty.");
        }
        return false;
    }
}
