package com.fan.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

/**
 * @author KeFan
 * @date 2023/7/3
 * @time 13:50
 */

@SpringBootTest
public class MeetingSystemTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void context() {

    }

    @Test
    void testPassword() {
        System.out.println(passwordEncoder.encode("111"));
    }

}
