package com.owuor.springJWT.service;

import com.owuor.springJWT.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY = "ba17f5a255c77e76d9a6e006d6e7dda72dc7cd5b4af245c0d65036b146e86ef1";

    public  String extractUsername (String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isValid (String token, UserDetails user){
        String userName = extractUsername(token);
        return (userName.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim (String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
    public String generateToken (User user) {
        String token = Jwts.builder().subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() +24*60*60*1000))
                .signWith(getSigningKey()).compact();
        return token;
    }
    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
