package com.wocks.coffeeshopback.user.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LoginResponseDto {
    private String userName;
    private String accessToken;
    private String refreshToken;
}
