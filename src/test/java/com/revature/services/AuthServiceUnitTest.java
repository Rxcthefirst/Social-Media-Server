package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;


@ExtendWith(SpringExtension.class)
public class AuthServiceUnitTest {

    @Mock
    private static UserService userService;

    @InjectMocks
    AuthService authService;

    @Test
    public void findByCredentialsCallsUserService(){
        User user = new User(0,"","","","");
        when(userService.findByCredentials("arg1", "arg2")).thenReturn(Optional.of(user));
        authService.findByCredentials("arg1", "arg2");
        verify(userService).findByCredentials("arg1", "arg2");
    }

    @Test
    public void loginReturnsUserIfPresent(){
        User user = new User(0,"","","","");
        when(userService.findByCredentials("arg1", "arg2")).thenReturn(Optional.of(user));
        try {
            User result = authService.login("arg1", "arg2");
            assertEquals(user, result); 
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void loginThrowsIfUserNotFound(){
        when(userService.findByCredentials("arg1", "arg2")).thenReturn(Optional.empty());
        try {
            authService.login("arg1", "arg2");
            fail();
        } catch (UserNotFoundException e) {
            return;
        }
    }

    @Test
    public void registerCallsSave(){
        User user = new User(0,"","","","");
        try {
            when(userService.save(user)).thenReturn(user);
            authService.register(user);
            verify(userService).save(user);
        } catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void registerThowsIfEmailTaken(){
        User user = new User(0,"","","","");
        try {
            when(userService.save(user)).thenThrow(EmailReservedException.class);
            authService.register(user);
            fail();
        } catch (EmailReservedException e) {
            e.printStackTrace();
            return;
        }
    }
}
