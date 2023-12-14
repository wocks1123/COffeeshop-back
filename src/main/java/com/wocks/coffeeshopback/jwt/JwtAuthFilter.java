package com.wocks.coffeeshopback.jwt;

import java.io.IOException;
import java.net.Authenticator;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = parseHeader(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String userName = jwtProvider.validateToken(token);
        if (userName == null) {
            jwtExceptionHandler(response, "Token Expired", HttpStatus.UNAUTHORIZED);
            return;
        }

        setAuthentication(userName);

        filterChain.doFilter(request,response);
    }


    private String parseHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
            .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
            .map(token -> token.substring(7))
            .orElse(null);
    }


    private void setAuthentication(String userName) {
        Authentication authentication = jwtProvider.createAuthentication(userName);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper()
                .writeValueAsString(new ErrorResponse(status.value(), msg));
            response.getWriter().write(json);
        } catch (Exception e) {

        }
    }


    @Data
    public static class ErrorResponse{
        private final Integer code;
        private final String message;
    }

}
