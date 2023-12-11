package com.exam.examserver.security;

import com.exam.examserver.config.ApplicationConstans;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenHelper {

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(ApplicationConstans.SECRET).parseClaimsJws(token).getBody();
    }
    public String getUsernameFromToken(String token){
        return this.getAllClaimsFromToken(token).getSubject();
    }
    public Date getExpirationTimeFromToken(String token){
        return this.getAllClaimsFromToken(token).getExpiration();
    }
    public boolean isExpired(String token){
        return this.getExpirationTimeFromToken(token).before(new Date());
    }
    public String generateToken(String subject){
        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ApplicationConstans.JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512,ApplicationConstans.SECRET)
                .compact();
    }
    public boolean validateToken(String token){
        return (!this.isExpired(token));
    }

}
