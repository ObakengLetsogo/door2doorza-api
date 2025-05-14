package com.door2doorza.door2doorza_api.controller;

import com.door2doorza.door2doorza_api.model.User;
import com.door2doorza.door2doorza_api.security.JwtUtils;
import com.door2doorza.door2doorza_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }
        
        Optional<User> userOptional = userService.getUserByEmail(email);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate JWT token
                String token = jwtUtils.generateToken(user.getEmail());
                
                // Create response with user info and token
                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("token", token);
                
                return ResponseEntity.ok(response);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Check if email already exists
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        
        // Create the user
        User createdUser = userService.createUser(user);
        
        // Generate JWT token
        String token = jwtUtils.generateToken(createdUser.getEmail());
        
        // Create response with user info and token
        Map<String, Object> response = new HashMap<>();
        response.put("user", createdUser);
        response.put("token", token);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}