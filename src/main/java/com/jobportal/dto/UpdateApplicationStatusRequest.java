package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {

    @NotNull
    private ApplicationStatus status;
}

