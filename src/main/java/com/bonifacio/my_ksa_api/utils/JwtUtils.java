package com.bonifacio.my_ksa_api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${time.expiration}")
    private String timeExpiration;
    @Value("${jwt.user.creator}")
    private String userCreator;

    public String generateToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return JWT.create()
                .withIssuer(this.userCreator)
                .withSubject(username)
                .withClaim("authorities",authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+Long.parseLong(this.timeExpiration)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }
    public DecodedJWT validateJWT(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(this.userCreator)
                    .build();
            return jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new JWTVerificationException(e.getMessage());
        }
    }
    public String getUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }
    public Claim getClaim(DecodedJWT decodedJWT,String claims){
        return decodedJWT.getClaim(claims);
    }
    public Map<String,Claim> getClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
