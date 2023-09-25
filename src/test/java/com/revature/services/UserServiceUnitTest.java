package com.revature.services;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.EmailReservedException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceUnitTest {
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void findByCredentialsCallsFindByEmailAndPassword() {
        User user = new User(0,"","","","");
        when(userRepository.findByEmailAndPassword("arg1", "arg2"))
            .thenReturn(Optional.of(user));
        userService.findByCredentials("arg1", "arg2");
        verify(userRepository).findByEmailAndPassword("arg1", "arg2");
    }

    @Test
    public void saveCallsSaveIfEmailNotPresent() {
        User user = new User(0,"","","","");
        when(userRepository.findByEmail("")).thenReturn(Optional.empty());
        try {
            userService.save(user);
            verify(userRepository).save(user);
        } catch (EmailReservedException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void saveThrowsIfEmailTaken() {
        User user = new User(0,"","","","");
        when(userRepository.findByEmail("")).thenReturn(Optional.of(user));
        try {
            userService.save(user);
            fail();
        } catch (EmailReservedException e) {
            e.printStackTrace();
            return;
        }
    }
    
    @Test
    public void findByIdCallsRepositoryFindById() {
    	User user = new User(0,"","","","");
    	when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    	userService.findById(user.getId());
    	verify(userRepository).findById(user.getId());
    }
}
