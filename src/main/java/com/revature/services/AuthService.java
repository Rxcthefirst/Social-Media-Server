package com.revature.services;

import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    public User login(String login, String password) throws UserNotFoundException {
        Optional<User> optional = findByCredentials(login, password);

        if(!optional.isPresent()) 
            throw new UserNotFoundException("A user with such credentials has not been found.");

        return optional.get();
    }

    public User register(User user) throws EmailReservedException {
        return userService.save(user);
    }
}
