package com.gymnasio.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
  private final Key key; private final long expirationMs;
  public JwtTokenProvider(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long exp) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expirationMs = exp;
  }
  public String generar(String username, String rol){
    Date now = new Date(), exp = new Date(now.getTime()+expirationMs);
    return Jwts.builder().setSubject(username).claim("rol", rol).setIssuedAt(now).setExpiration(exp).signWith(key, SignatureAlgorithm.HS256).compact();
  }
  public String getUsername(String token){ return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject(); }
  public boolean validar(String token){
    try { Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); return true; }
    catch (JwtException | IllegalArgumentException e){ return false; }
  }
}

