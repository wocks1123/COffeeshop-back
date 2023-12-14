package com.wocks.coffeeshopback.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseException {

    HttpStatus getHttpStatus();

    String getMessage();
}
