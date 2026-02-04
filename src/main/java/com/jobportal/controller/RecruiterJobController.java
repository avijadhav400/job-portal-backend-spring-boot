package com.jobportal.controller;

import com.jobportal.dto.ApplicantResponseDTO;
import com.jobportal.dto.CreateJobRequest;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.security.CustomUserPrincipal;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/jobs")
public class RecruiterJobController {

    @Autowired
    private JobService jobService;

    @Autowired
    JobApplicationRepository applicationRepository;

    // 1. Post Job
    @PostMapping
    public Job postJob(@RequestBody @Valid CreateJobRequest request, Authentication authentication) {
        // ✅ Extract recruiter ID from JWT token
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        Long recruiterId = principal.getUserId();

        return jobService.createJob(request, recruiterId);
    }

    // 2. View My Jobs
    @GetMapping
    public List<Job> myJobs(Authentication authentication) {
        // ✅ Extract recruiter ID from JWT token
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        Long recruiterId = principal.getUserId();

        return jobService.getRecruiterJobs(recruiterId);
    }

    // 3. Close Job
    @PostMapping("/close/{jobId}")
    public Job closeJob(@PathVariable Long jobId) {
        return jobService.closeJob(jobId);
    }


    @GetMapping("/applicants")
    public List<ApplicantResponseDTO> viewApplicants(Authentication authentication) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        return applicationRepository.findApplicantsByRecruiterId(principal.getUserId());
    }

    @PostMapping("/applications/{id}/shortlist")
    public void shortlist(@PathVariable Long id, Authentication authentication) {
        updateStatus(id, ApplicationStatus.SHORTLISTED, authentication);
    }

    @PostMapping("/applications/{id}/reject")
    public void reject(@PathVariable Long id, Authentication authentication) {
        updateStatus(id, ApplicationStatus.REJECTED, authentication);
    }

    private void updateStatus(
            Long applicationId,
            ApplicationStatus status,
            Authentication authentication
    ) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        JobApplication application =
                applicationRepository.findByIdAndRecruiterId(
                        applicationId,
                        principal.getUserId()
                ).orElseThrow(() -> new RuntimeException("Unauthorized"));

        application.setApplicationStatus(status);
        applicationRepository.save(application);
    }

}

