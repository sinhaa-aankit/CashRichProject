package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        // Perform password encryption and other business logic
        // Save user to database
        return userRepository.save(user);
    }
    
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
    
    public User updateUser(String username, User updatedUser) throws NotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstname(updatedUser.getFirstname());
            user.setLastname(updatedUser.getLastname());
            user.setMobile(updatedUser.getMobile());
            user.setEmail(updatedUser.getEmail());

            return userRepository.save(user);
        } else {
            throw new NotFoundException();
        }
    }


}

