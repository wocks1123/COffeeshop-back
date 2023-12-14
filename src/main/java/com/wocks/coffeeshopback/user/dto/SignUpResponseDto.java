package com.wocks.coffeeshopback.user.dto;

import com.wocks.coffeeshopback.user.domain.User;

import lombok.Builder;
import lombok.Getter;


@Getter
public class SignUpResponseDto {
    private final String userName;
    private final String nickName;
    private final String email;
    private final String profile;

    @Builder
    public SignUpResponseDto(User user) {
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.profile = user.getProfile();
    }
}
