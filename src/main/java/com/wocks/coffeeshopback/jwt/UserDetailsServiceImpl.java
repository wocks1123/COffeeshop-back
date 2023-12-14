package com.wocks.coffeeshopback.jwt;


import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.wocks.coffeeshopback.common.exception.RestApiException;
import com.wocks.coffeeshopback.user.domain.User;
import com.wocks.coffeeshopback.user.exception.UserException;
import com.wocks.coffeeshopback.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.getByUserName(username);

        if (user.isEmpty()) {
            throw new RestApiException(UserException.USER_NOT_FOUND);
        }

        return new UserDetailsImpl(user.get());
    }
}
