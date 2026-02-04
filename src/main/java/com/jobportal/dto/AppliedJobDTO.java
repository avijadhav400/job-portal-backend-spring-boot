package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;
import java.time.LocalDateTime;

public class AppliedJobDTO {

    private Long jobId;
    private String jobTitle;                 // ✅ FIXED
    private ApplicationStatus applicationStatus; // ✅ ADDED
    private LocalDateTime appliedAt;

    // getters & setters

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}