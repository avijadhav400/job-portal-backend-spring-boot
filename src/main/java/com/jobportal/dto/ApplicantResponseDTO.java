package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantResponseDTO {

    private Long applicationId;
    private Long jobId;
    private String jobTitle;

    private Long seekerId;
    private String seekerName;
    private String seekerEmail;

    private ApplicationStatus applicationStatus;
}
