package com.practice.poll.practice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider { //majorly for generating and verifying jwt

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

   @Value("${app.jwtSecret}")
    private String jwtSecret;

   @Value("${app.jwtExpirationInMs}")
   private int jwtExpirationInMs;

   public String generateToken(Authentication authentication){

       UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

       Date date = new Date();
       Date expiryDate = new Date(date.getTime() + jwtExpirationInMs);

       return Jwts.builder()
               .setSubject(Long.toString(userPrincipal.getId()))
               .setIssuedAt(new Date())
               .setExpiration(expiryDate)
            //   .signWith(SignatureAlgorithm.HS512,jwtSecret)
               .signWith(SignatureAlgorithm.HS512,jwtSecret)
               .compact();
   }

   public Long getUserIdFromJwt(String token){ //like the name implies
       Claims claims = Jwts.parser()
               .setSigningKey(jwtSecret)
               .parseClaimsJws(token)
               .getBody();

       return Long.parseLong(claims.getSubject());
   }

   public Boolean validateToken(String authToken){ //validation only
    try {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
    }catch (SignatureException exception){
        logger.error("JWT SIGNATURE IS INVALID");

    }catch (MalformedJwtException exception){
        logger.error("JWT TOKEN IS INVALID");

    }catch (ExpiredJwtException exception){
        logger.error("JWT TOKEN IS EXPIRED");

    }catch (UnsupportedJwtException exception){
        logger.error("JWT TOKEN IS UNSUPPORTED");

    }catch (IllegalArgumentException exception){
        logger.error("JWT CLAIMS STRING IS EMPTY");
    }

    return false;
   }


}
