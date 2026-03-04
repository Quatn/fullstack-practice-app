package com.example.demo_springboot_api.common.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;

@Component
public class JwtService {

  @Value("${JWT_SECRET}")
  private String SECRET;

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim("roles", userDetails.getAuthorities())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(getSignKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
