package com.wocks.coffeeshopback.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wocks.coffeeshopback.user.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> getByUserName(String userName);

    public Optional<User> getByUserNameAndIsDeletedFalse(String userName);

    public Optional<User> getByEmail(String email);

    public Optional<User> getByNickName(String nickName);

    public boolean existsByNickName(String nickName);

    public boolean existsByEmail(String email);

}
