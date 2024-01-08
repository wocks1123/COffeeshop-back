package com.wocks.coffeeshopback.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wocks.coffeeshopback.user.domain.User;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    @DisplayName("유저 생성")
    void addUser() {
        userRepository.save(
            User.builder()
                .userName("test1")
                .email("test1@test.com")
                .nickName("testNickName1")
                .profile("")
                .password("1234")
                .build()
        );
    }


    @Test
    @DisplayName("계정으로 검색")
    void findByUsername() {
        String username = "test1";

        User user = userRepository.getByUserName(username)
            .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));

        assertNotNull(user);
        assertEquals(user.getUserName(), "test1");
    }


    @Test
    @DisplayName("존재하지 않은 계정으로 검색")
    void findByUsernameFail() {
        String username = "test2";

        assertThrows(
            RuntimeException.class,
            () -> userRepository.getByUserName(username)
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다.")),
            "계정이 존재하지 않습니다."
        );
    }


}
