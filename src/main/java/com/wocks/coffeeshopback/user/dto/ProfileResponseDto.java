package com.wocks.coffeeshopback.user.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ProfileResponseDto {
    private final String nickName;
    private final String email;
    private final String profile;
}
