package com.revature.services;

import com.revature.exceptions.EmailReservedException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) throws EmailReservedException {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailReservedException("The " + user.getEmail() + " email has already been used.");

        return userRepository.save(user);
    }

    public Optional<User> findById(int id) {
        return this.userRepository.findById(id);
    }
}
