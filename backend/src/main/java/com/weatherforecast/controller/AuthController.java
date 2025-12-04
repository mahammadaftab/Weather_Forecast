package com.weatherforecast.controller;

import com.weatherforecast.dto.LoginDTO;
import com.weatherforecast.dto.RegisterDTO;
import com.weatherforecast.dto.JwtResponseDTO;
import com.weatherforecast.model.User;
import com.weatherforecast.service.impl.UserServiceImpl;
import com.weatherforecast.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(loginDto.getUsername());
        
        // Get user details
        User user = userService.getUserByUsername(loginDto.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(new JwtResponseDTO(jwt, user.getUsername(), user.getEmail()));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDto) {
        // Check if username or email already exists
        if (userService.getUserByUsername(registerDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        
        if (userService.getUserByEmail(registerDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        
        // Create new user
        User user = new User(registerDto.getUsername(), registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        
        userService.registerUser(user, registerDto.getPassword());
        
        return ResponseEntity.ok("User registered successfully!");
    }
}