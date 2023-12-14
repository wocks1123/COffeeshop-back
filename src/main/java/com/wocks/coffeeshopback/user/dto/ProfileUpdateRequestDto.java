package com.wocks.coffeeshopback.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileUpdateRequestDto {
    private final String nickName;
    private final String email;
    private final String profile;
}
