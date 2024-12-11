package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    // Đăng ký người dùng
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Đăng nhập người dùng
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        User authenticatedUser = userService.authenticateUser(user);
        if (authenticatedUser != null) {
            String token = generateToken(authenticatedUser);

            // Trả thông tin người dùng và token
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("token", token);
            response.put("user", Map.of(
                    "id", authenticatedUser.getId(),
                    "name", authenticatedUser.getName(),
                    "email", authenticatedUser.getEmail()
            ));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }
    }

    private String generateToken(User user) {
        // Logic để tạo JWT token
        return "dummy-jwt-token";
    }
}
