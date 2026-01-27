package com.jobportal.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateJobRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String skills;

    @NotBlank
    private String location;

    @NotNull
    @Min(0)
    private Integer experience;

    @NotNull
    private Integer minSalary;

    @NotNull
    private Integer maxSalary;
}
