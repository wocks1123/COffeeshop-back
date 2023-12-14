package com.wocks.coffeeshopback.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequestDto {
    private final String originPassword;
    private final String newPassword;
    private final String newPasswordCheck;
}
