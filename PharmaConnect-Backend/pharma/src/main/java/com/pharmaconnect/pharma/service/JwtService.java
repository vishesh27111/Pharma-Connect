package com.pharmaconnect.pharma.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${token.secret.key}")
  String jwtSecretKey;

  @Value("${token.expirationms}")
  Long jwtExpirationMs;

  public String extractUserName(String token) {
      return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(UserDetails userDetails) {
      return generateToken(new HashMap<>(), userDetails);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
      final String userName = extractUserName(token);
      return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
      final Claims claims = extractAllClaims(token);
      return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    JwtBuilder jwtBuilder = Jwts.builder();

    // Set extra claims
    jwtBuilder.setClaims(extraClaims);

    // Set subject
    jwtBuilder.setSubject(userDetails.getUsername());

    // Set issued and expiration dates
    Date now = new Date(System.currentTimeMillis());
    jwtBuilder.setIssuedAt(now);
    jwtBuilder.setExpiration(new Date(now.getTime() + jwtExpirationMs));

    // Sign with the key
    jwtBuilder.signWith(getSigningKey(), SignatureAlgorithm.HS256);
    
    return jwtBuilder.compact();
  }

  private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
      return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
      return Keys.hmacShaKeyFor(keyBytes);
  }
  
}