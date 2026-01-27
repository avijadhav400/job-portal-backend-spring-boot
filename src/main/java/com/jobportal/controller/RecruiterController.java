package com.jobportal.controller;

import com.jobportal.dto.ApplicantResponseDTO;
import com.jobportal.dto.UpdateApplicationStatusRequest;
import com.jobportal.entity.Job;
import com.jobportal.service.JobApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{recruiterId}/applicants")
    public List<ApplicantResponseDTO> viewApplicants(
            @PathVariable Long recruiterId
    ) {
        return recruiterService.viewApplicants(recruiterId);
    }

    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam Long recruiterId,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        jobApplicationService.updateApplicationStatus(
                recruiterId,
                applicationId,
                request.getStatus()
        );

        return ResponseEntity.ok("Application status updated successfully");
    }

}
