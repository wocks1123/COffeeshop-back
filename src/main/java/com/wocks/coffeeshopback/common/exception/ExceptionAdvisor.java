package com.wocks.coffeeshopback.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wocks.coffeeshopback.common.response.ResponseBody;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;


@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception
    ) {
        return ResponseEntity.badRequest().body(
            ResponseBody.validationError(exception.getBindingResult())
        );
    }


    @ExceptionHandler(RestApiException.class)
    public final ResponseEntity<ResponseBody<?>> handleRestApiException(
        RestApiException exception
    ) {
        BaseException baseException = exception.getBaseException();

        ResponseBody<?> responseBody = ResponseBody.builder()
            .status(baseException.getHttpStatus())
            .message(baseException.getMessage())
            .build();

        return new ResponseEntity<>(
            responseBody,
            baseException.getHttpStatus()
        );
    }


    @ExceptionHandler({SignatureException.class, MalformedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<ResponseBody<?>> handleJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ResponseBody.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Invalid Token")
                    .build()
            );
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseBody<?>> handleAuthenticationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ResponseBody.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Authentication failed at controller advice")
                    .build()
            );
    }

}
