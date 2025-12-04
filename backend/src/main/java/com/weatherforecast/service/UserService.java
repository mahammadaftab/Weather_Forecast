package com.weatherforecast.service;

import com.weatherforecast.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User registerUser(User user, String rawPassword);
    Optional<User> authenticateUser(String username, String rawPassword);
    Optional<User> getUserById(String id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User updateUser(User user);
    void deleteUser(String id);
    boolean changePassword(String userId, String oldPassword, String newPassword);
}