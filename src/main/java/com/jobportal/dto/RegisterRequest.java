package com.jobportal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobportal.entity.Role;
import com.jobportal.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumeratedValue;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    @JsonProperty("full_name")
    private String fullName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
