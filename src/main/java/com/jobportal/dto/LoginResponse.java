package com.jobportal.dto;

import com.jobportal.entity.Role;
import com.jobportal.entity.Status;

public class LoginResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private Status status;

    public LoginResponse(Long id, String fullName, String email, Role role, Status status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    // getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public Status getStatus() { return status; }
}
