package com.wocks.coffeeshopback.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequestDto {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotNull(message = "계정을 입력해주세요")
    private String userName;

    @NotNull(message = "별명을 입력해주세요")
    private String nickName;

    private String profile;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "동일한 비밀번호를 입력해주세요.")
    private String passwordCheck;

}
