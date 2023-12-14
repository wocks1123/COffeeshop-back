package com.wocks.coffeeshopback.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.wocks.coffeeshopback.jwt.entity.RefreshToken;


public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserName(String userName);

    Optional<RefreshToken> deleteByUserName(String userName);
}
