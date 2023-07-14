package com.chatHub.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: xsz
 * @Description: used to generate tokens and verify tokens
 * @DateTime: 2023/6/28 20:36
 **/

@Service
@Slf4j
public class TokenService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;

    // Generate token, including username, generation time, expiration time, secret encryption
    public String generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //extract username from token
    public String extractUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //Determine whether the token is right
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e ){
            return  false;
        }
    }

    //Check if the token is expired
    public boolean isTokenExpired(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            Date expirationDate = claims.getExpiration();
            Date currenDate = new Date();
            log.info(expirationDate.toString());
            return expirationDate.before(currenDate);
        }catch (Exception e){
            log.error("An error occurred while checking token expiration");
            log.error(e.toString());
            return true;
        }
    }
}
