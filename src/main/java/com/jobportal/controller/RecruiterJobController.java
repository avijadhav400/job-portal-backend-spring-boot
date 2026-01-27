package com.jobportal.controller;

import com.jobportal.dto.CreateJobRequest;
import com.jobportal.entity.Job;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/jobs")
public class RecruiterJobController {

    @Autowired
    private JobService jobService;

    // 1. Post Job
    @PostMapping
    public Job postJob(@RequestParam Long recruiterId,
                       @RequestBody @Valid CreateJobRequest request) {
        return jobService.createJob(request, recruiterId);
    }

    // 2. View My Jobs
    @GetMapping("/{recruiterId}")
    public List<Job> myJobs(@PathVariable Long recruiterId) {
        return jobService.getRecruiterJobs(recruiterId);
    }

    // 3. Close Job
    @PostMapping("/close/{jobId}")
    public Job closeJob(@PathVariable Long jobId) {
        return jobService.closeJob(jobId);
    }
}

