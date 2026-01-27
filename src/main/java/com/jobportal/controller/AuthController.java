package com.jobportal.controller;

import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.LoginResponse;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

}
