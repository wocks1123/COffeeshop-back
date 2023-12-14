package com.wocks.coffeeshopback.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wocks.coffeeshopback.common.response.ResponseBody;
import com.wocks.coffeeshopback.jwt.CurrentUser;
import com.wocks.coffeeshopback.user.dto.ChangePasswordRequestDto;
import com.wocks.coffeeshopback.user.dto.LoginRequestDto;
import com.wocks.coffeeshopback.user.dto.LoginResponseDto;
import com.wocks.coffeeshopback.user.dto.LogoutResponseDto;
import com.wocks.coffeeshopback.user.dto.ProfileResponseDto;
import com.wocks.coffeeshopback.user.dto.ProfileUpdateRequestDto;
import com.wocks.coffeeshopback.user.dto.SignUpRequestDto;
import com.wocks.coffeeshopback.user.dto.SignUpResponseDto;
import com.wocks.coffeeshopback.user.service.LoginService;
import com.wocks.coffeeshopback.user.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseBody<?>> signUp(@RequestBody @Valid SignUpRequestDto dto) {
        SignUpResponseDto responseDto = loginService.signUp(dto);

        ResponseBody<SignUpResponseDto> responseBody = ResponseBody.<SignUpResponseDto>builder()
            .status(HttpStatus.CREATED)
            .message("계정이 생성되었습니다.")
            .data(responseDto)
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseBody<?>> login(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto responseDto = loginService.login(dto);

        ResponseBody<LoginResponseDto> responseBody = ResponseBody.<LoginResponseDto>builder()
            .status(HttpStatus.OK)
            .message("로그인에 성공했습니다.")
            .data(responseDto)
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<ResponseBody<?>> logout() {
        LogoutResponseDto responseDto = loginService.logout("test_name3");

        ResponseBody<LogoutResponseDto> responseBody = ResponseBody.<LogoutResponseDto>builder()
            .status(HttpStatus.OK)
            .message("로그아웃에 성공했습니다.")
            .data(responseDto)
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/{userName}")
    public ResponseEntity<ResponseBody<?>> profile(
        @PathVariable("userName") String userName
    ) {
        ProfileResponseDto responseDto = userService.getProfileByUserName(userName);

        ResponseBody<ProfileResponseDto> responseBody = ResponseBody.<ProfileResponseDto>builder()
            .status(HttpStatus.OK)
            .message("사용자의 프로필을 가져왔습니다.")
            .data(responseDto)
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @PutMapping("/profile")
    public ResponseEntity<ResponseBody<?>> updateProfile(
        @RequestBody @Valid ProfileUpdateRequestDto dto,
        @CurrentUser String userName
    ) {
        ProfileResponseDto responseDto = userService.updateProfile(dto, userName);

        ResponseBody<ProfileResponseDto> responseBody = ResponseBody.<ProfileResponseDto>builder()
            .status(HttpStatus.OK)
            .message("사용자의 프로필을 업데이트했습니다.")
            .data(responseDto)
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @PutMapping("/password")
    public ResponseEntity<ResponseBody<?>> changePassword(
        @RequestBody @Valid ChangePasswordRequestDto dto,
        @CurrentUser String userName
    ) {
        userService.changePassword(dto, userName);

        ResponseBody<String> responseBody = ResponseBody.<String>builder()
            .status(HttpStatus.OK)
            .message("비밀번호를 변경했습니다.")
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody<?>> deleteUser(
        @CurrentUser String userName
    ) {
        userService.deleteUser(userName);

        ResponseBody<String> responseBody = ResponseBody.<String>builder()
            .status(HttpStatus.OK)
            .message("계정을 삭제했습니다.")
            .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
