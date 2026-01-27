package com.jobportal.service;


import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.LoginResponse;
import com.jobportal.entity.Role;
import com.jobportal.entity.Status;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private  UserRepository userRepository;

    public User register(User user) {
        if (user.getRole() == Role.RECRUITER) {
            user.setStatus(Status.PENDING);
        } else {
            user.setStatus(Status.ACTIVE);
        }
        User savedUser = userRepository.save(user); // ✅ persist

        return savedUser; // ✅ return managed entity
    }

    public User login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        if (user.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Account not active");
        }

        return user;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Account not active. Current status: " + user.getStatus());
        }

        return new LoginResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }
}
