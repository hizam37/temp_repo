package com.social.Hizam.service;

import com.social.Hizam.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.signing.refreshToken}")
    private String refreshToken;


    public String extractUserName(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }

    private SecretKey getSigningKey()
    {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private SecretKey getRefreshToken()
    {
        byte[] keyBytes = Decoders.BASE64URL.decode(refreshToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){

        return Jwts.builder().claims(extractClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(getSigningKey(),Jwts.SIG.HS256).compact();
    }

    private String generateRefreshToken(Map<String,Object> extractClaims, UserDetails userDetails){

        return Jwts.builder().claims(extractClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(getSigningKey(),Jwts.SIG.HS256).compact();
    }


    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims = new HashMap<>();
        if(userDetails instanceof User customizedUserDetails){
            claims.put("id",customizedUserDetails.getId());
            claims.put("email",customizedUserDetails.getEmail());
            claims.put("role",customizedUserDetails.getRole());
        }
        return generateToken(claims,userDetails);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }


    private Date extractExpiration(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        final String userName =extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }





}
