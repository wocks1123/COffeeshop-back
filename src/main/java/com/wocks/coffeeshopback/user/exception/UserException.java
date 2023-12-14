package com.wocks.coffeeshopback.user.exception;

import org.springframework.http.HttpStatus;

import com.wocks.coffeeshopback.common.exception.BaseException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserException implements BaseException {
    // 400
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이메일 중복"),
    DUPLICATE_USER_NAME(HttpStatus.BAD_REQUEST, "계정 중복"),
    DUPLICATE_NICK_NAME(HttpStatus.BAD_REQUEST, "별명 중복"),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "계정과 비밀번호를 확인해주세요."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "확인되지 않은 계정입니다."),
    LOGIN_REQUIRE(HttpStatus.BAD_REQUEST, "로그인이 필요합니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    WRONG_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "2차 비밀번호가 일치하지 않습니다."),

    // 401
    UNAUTHENTICATED_ACCESS(HttpStatus.UNAUTHORIZED, "인증 실패");

    private final HttpStatus httpStatus;
    private final String message;
}
