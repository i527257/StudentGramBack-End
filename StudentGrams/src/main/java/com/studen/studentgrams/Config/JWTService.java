package com.studen.studentgrams.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRETKEY = "a43690187793ff73ed97842d71c8c0ada979068a2b240797cb34d0f5d8596454";

    public String extractusername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractclaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractclaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(GetSigningKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Key GetSigningKey() {
        byte[] keybytes = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String GenerateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraclaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(GetSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean IsTokenValid(String token, UserDetails userDetails) {
        final String username = extractusername(token);
        return username.equals(userDetails.getUsername()) && !IsTokenExpired(token);
    }

    private boolean IsTokenExpired(String token) {
        return ExtractExperation(token).before(new Date());
    }

    private Date ExtractExperation(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
