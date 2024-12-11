
package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User authenticateUser(User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }
    public static String getRoleFromToken(String token) {
        // Giải mã JWT và lấy vai trò người dùng từ token
        // Mã giả dưới đây trả về 'admin' hoặc 'user' từ token
        return "admin"; // Trả về 'admin' hoặc 'user' dựa trên token thực tế
    }
}
