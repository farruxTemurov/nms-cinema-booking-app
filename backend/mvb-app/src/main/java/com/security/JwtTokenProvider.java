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
        // Ensure key is of sufficient length for HS512 (min 512 bits = 64 bytes)
        if (jwtSecret.length() < 64) {
            throw new IllegalArgumentException("JWT secret key must be at least 64 characters long for HS512.");
        }
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        // Extract roles
        var authorities = authentication.getAuthorities()
                .stream()
                .map(role -> role.getAuthority())
                .toList();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)  // ✅ Add roles here
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(email)
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
