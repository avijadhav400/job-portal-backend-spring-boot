package com.jobportal.controller;

import com.jobportal.dto.ApplicantResponseDTO;
import com.jobportal.dto.UpdateApplicationStatusRequest;
import com.jobportal.entity.Job;
import com.jobportal.security.CustomUserPrincipal;
import com.jobportal.service.JobApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private JobService jobService;

    @PostMapping("/job")
    public Job postJob(@RequestBody Job job) {
        return jobService.postJob(job);
    }

    @Autowired
    private RecruiterService recruiterService;

    @GetMapping("/applicants")
    public List<ApplicantResponseDTO> viewApplicants(Authentication authentication) {
        // ✅ Extract recruiter ID from JWT token
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        Long recruiterId = principal.getUserId();

        return recruiterService.viewApplicants(recruiterId);
    }

    @PatchMapping("/applications/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable("id") Long applicationId,
            @RequestBody UpdateApplicationStatusRequest request,
            Authentication authentication
    ) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        recruiterService.updateApplicationStatus(
                principal.getUserId(),
                applicationId,
                request.getStatus()
        );

        return ResponseEntity.ok().build();
    }

}
