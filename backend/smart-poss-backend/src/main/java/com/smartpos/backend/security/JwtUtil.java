//package com.smartpos.backend.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private final String SECRET = "smartpossmartpossmartpossmartpossmartpos123456";
//
//    private Key getSigningKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes());
//    }
//
//    // Generate Token
//    public String generateToken(String email) {
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    // Extract Email
//    public String extractEmail(String token) {
//
//        return extractClaims(token).getSubject();
//    }
//
//    // Extract Claims
//    private Claims extractClaims(String token) {
//
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
