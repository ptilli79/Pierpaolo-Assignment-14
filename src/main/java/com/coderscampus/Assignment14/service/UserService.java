package com.coderscampus.Assignment14.service;


import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String name) {
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, name);
        userRepository.save(user);
        return user;
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}