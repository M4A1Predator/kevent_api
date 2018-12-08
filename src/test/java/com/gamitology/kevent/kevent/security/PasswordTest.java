package com.gamitology.kevent.kevent.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {
    @Test
    public void checkPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");
        System.out.println(encodedPassword);
        boolean result = passwordEncoder.matches("password", encodedPassword);
        Assert.assertTrue(result);
    }
}
