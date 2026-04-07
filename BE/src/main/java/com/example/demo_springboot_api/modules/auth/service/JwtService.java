package com.example.demo_springboot_api.modules.auth.service;

import com.example.demo_springboot_api.modules.auth.constant.ModuleConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  @Value("${JWT_SECRET}")
  private String SECRET;

  @Value("${TOKEN_EXPIRATION_SECONDS}")
  private long TOKEN_EXPIRATION_SECONDS;

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim(ModuleConstants.JWT_TOKEN_ACCESS_PRIVILEGES_CLAIM, userDetails.getAuthorities())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * TOKEN_EXPIRATION_SECONDS))
        .signWith(getSignKey())
        .compact();
  }

  public String generateToken(
      String subject, Pair<String, String>[] claims, Date issuedAt, Date expiration) {
    JwtBuilder builder = Jwts.builder().subject(subject).issuedAt(issuedAt).expiration(expiration);

    for (Pair<String, String> claim : claims) {
      builder.claim(claim.getFirst(), claim.getSecond());
    }

    return builder.signWith(getSignKey()).compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
