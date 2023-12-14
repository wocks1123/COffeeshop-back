package com.wocks.coffeeshopback.comment.exception;


import org.springframework.http.HttpStatus;

import com.wocks.coffeeshopback.common.exception.BaseException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CommentException implements BaseException {

    // 404
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
