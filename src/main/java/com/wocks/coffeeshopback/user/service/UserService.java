package com.wocks.coffeeshopback.user.service;


import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wocks.coffeeshopback.common.exception.RestApiException;
import com.wocks.coffeeshopback.user.domain.User;
import com.wocks.coffeeshopback.user.dto.ChangePasswordRequestDto;
import com.wocks.coffeeshopback.user.dto.ProfileResponseDto;
import com.wocks.coffeeshopback.user.dto.ProfileUpdateRequestDto;
import com.wocks.coffeeshopback.user.exception.UserException;
import com.wocks.coffeeshopback.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public User getByUserName(String userName) {
        Optional<User> foundUser = userRepository.getByUserNameAndIsDeletedFalse(userName);
        if (foundUser.isEmpty()) {
            throw new RestApiException(UserException.USER_NOT_FOUND);
        }

        User user = foundUser.get();

        if (user.isDeleted()) {
            throw new RestApiException(UserException.USER_NOT_FOUND);
        }

        return user;
    }


    public ProfileResponseDto getProfileByUserName(String userName) {
        User user = getByUserName(userName);

        return ProfileResponseDto.builder().nickName(user.getNickName()).email(user.getEmail())
            .profile(user.getProfile()).build();
    }


    @Transactional
    public ProfileResponseDto updateProfile(ProfileUpdateRequestDto dto, String userName) {
        String nickName = dto.getNickName();
        String email = dto.getNickName();

        User user = getByUserName(userName);

        if (email != null && !email.equals(user.getEmail()) && userRepository.existsByEmail(
            email)) {
            throw new RestApiException(UserException.DUPLICATE_EMAIL);
        }
        if (nickName != null && !nickName.equals(user.getNickName())
            && userRepository.existsByNickName(nickName)) {
            throw new RestApiException(UserException.DUPLICATE_NICK_NAME);
        }

        user.update(dto);

        return ProfileResponseDto.builder().nickName(user.getNickName()).email(user.getEmail())
            .profile(user.getProfile()).build();
    }


    @Transactional
    public void changePassword(ChangePasswordRequestDto dto, String userName) {
        User user = getByUserName(userName);

        if (!passwordEncoder.matches(dto.getOriginPassword(), user.getPassword())) {
            throw new RestApiException(UserException.WRONG_PASSWORD);
        }

        if (dto.getNewPassword().equals(dto.getNewPasswordCheck())) {
            throw new RestApiException(UserException.WRONG_PASSWORD_CHECK);
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }


    @Transactional
    public void deleteUser(String userName) {
        User user = getByUserName(userName);
        user.delete();
    }
}
