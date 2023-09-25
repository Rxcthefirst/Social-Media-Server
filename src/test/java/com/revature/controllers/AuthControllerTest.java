package com.revature.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(authController).isNotNull();
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void register() {
    }
}