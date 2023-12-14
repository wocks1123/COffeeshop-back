package com.wocks.coffeeshopback.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.wocks.coffeeshopback.jwt.dto.TokenDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret}")
    private String secret;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // ACCESS_TIME 10초*100
    private static final long ACCESS_EXPIRE_TIME = 60 * 60 * 1000L;
    // REFRESH_TIME 60초*100
    private static final long REFRESH_EXPIRE_TIME = 60 * 60 * 24 * 1000L;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenDto createAllToken(String userName) {
        return new TokenDto(
            createToken(userName, "Access"), createToken(userName, "Refresh")
        );
    }


    public String createToken(String userName, String type) {
        //현재 시각
        Date date = new Date();
        //지속 시간
        long time = type.equals("Access") ? ACCESS_EXPIRE_TIME : REFRESH_EXPIRE_TIME;

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(userName)
            .setExpiration(new Date(date.getTime() + time))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
    }


    public String validateToken(String token) {
        try {
            String userName = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            return userName;
        } catch (Exception ex) {
            return null;
        }
    }


    // 인증 객체 생성
    public Authentication createAuthentication(String userName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
