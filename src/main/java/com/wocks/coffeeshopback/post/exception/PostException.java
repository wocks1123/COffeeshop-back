package com.wocks.coffeeshopback.post.exception;


import org.springframework.http.HttpStatus;

import com.wocks.coffeeshopback.common.exception.BaseException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostException implements BaseException {
    // 400
    POST_BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청이 올바르지 않습니다."),

    // 404
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
