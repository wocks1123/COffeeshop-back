package com.wocks.coffeeshopback.common.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseBody<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public static ResponseBody<?> validationError(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseBody<>(HttpStatus.BAD_REQUEST, "올바른 데이터 형식이 아닙니다.", map);
    }

}
