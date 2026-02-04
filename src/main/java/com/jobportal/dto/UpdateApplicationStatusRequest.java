package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateApplicationStatusRequest {

    @NotNull
    private ApplicationStatus status;
}

