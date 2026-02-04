package com.jobportal.dto;

import lombok.Data;

@Data
public class JobDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private Integer experience;
    private Integer minSalary;
    private Integer maxSalary;
    private String skills;
    private String status;

    // ⭐ THIS IS THE KEY FIELD
    private boolean alreadyApplied;
}