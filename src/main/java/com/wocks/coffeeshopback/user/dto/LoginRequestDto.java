package com.wocks.coffeeshopback.user.dto;


import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "계정을 입력해주세요")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
