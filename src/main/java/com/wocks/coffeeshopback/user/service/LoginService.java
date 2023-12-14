package com.wocks.coffeeshopback.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wocks.coffeeshopback.common.exception.RestApiException;
import com.wocks.coffeeshopback.jwt.JwtProvider;
import com.wocks.coffeeshopback.jwt.dto.TokenDto;
import com.wocks.coffeeshopback.jwt.entity.RefreshToken;
import com.wocks.coffeeshopback.jwt.repository.RefreshTokenRepository;
import com.wocks.coffeeshopback.user.domain.User;
import com.wocks.coffeeshopback.user.dto.LoginRequestDto;
import com.wocks.coffeeshopback.user.dto.LoginResponseDto;
import com.wocks.coffeeshopback.user.dto.LogoutResponseDto;
import com.wocks.coffeeshopback.user.dto.SignUpRequestDto;
import com.wocks.coffeeshopback.user.dto.SignUpResponseDto;
import com.wocks.coffeeshopback.user.exception.UserException;
import com.wocks.coffeeshopback.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto dto) {
        String userName = dto.getUserName();
        String nickName = dto.getNickName();
        String email = dto.getEmail();
        String password = dto.getPassword();
        String passwordCheck = dto.getPasswordCheck();

        if (!password.equals(passwordCheck)) {
            throw new RestApiException(UserException.WRONG_PASSWORD_CHECK);
        }

        if (userRepository.getByUserName(userName).isPresent()) {
            throw new RestApiException(UserException.DUPLICATE_USER_NAME);
        }

        if (userRepository.getByEmail(email).isPresent()) {
            throw new RestApiException(UserException.DUPLICATE_EMAIL);
        }

        if (userRepository.getByNickName(nickName).isPresent()) {
            throw new RestApiException(UserException.DUPLICATE_NICK_NAME);
        }

        User user = User.builder().userName(userName).nickName(nickName).email(email).profile("")
            .password(passwordEncoder.encode(password)).build();

        User result = userRepository.save(user);

        return SignUpResponseDto.builder().user(result).build();
    }


    @Transactional
    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<User> user = userRepository.getByUserNameAndIsDeletedFalse(dto.getUserName());
        if (user.isEmpty()) {
            throw new RestApiException(UserException.LOGIN_FAIL);
        }

        User existUser = user.get();

        if (!passwordEncoder.matches(dto.getPassword(), existUser.getPassword())) {
            throw new RestApiException(UserException.LOGIN_FAIL);
        }

        TokenDto token = jwtProvider.createAllToken(existUser.getUserName());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserName(
            existUser.getUserName());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(token.getRefreshToken()));
        } else {
            refreshTokenRepository.save(RefreshToken.builder().refreshToken(token.getRefreshToken())
                .userName(existUser.getUserName()).build());
        }

        return LoginResponseDto.builder().userName(existUser.getUserName())
            .accessToken(token.getAccessToken()).refreshToken(token.getRefreshToken()).build();
    }


    @Transactional
    public LogoutResponseDto logout(String userName) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserName(userName);
        if (refreshToken.isEmpty()) {
            throw new RestApiException(UserException.LOGIN_REQUIRE);
        }

        String tokenUserName = refreshToken.get().getUserName();
        if (!userName.equals(tokenUserName)) {
            throw new RestApiException(UserException.UNAUTHENTICATED_ACCESS);
        }

        return LogoutResponseDto.builder().userName(tokenUserName).build();
    }

}
