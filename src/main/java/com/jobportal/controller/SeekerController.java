package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seeker")
public class SeekerController {

    @Autowired
    private JobService jobService;

    @GetMapping("/jobs")
    public List<Job> viewJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping("/apply")
    public void apply(@RequestParam Long jobId,
                      @RequestParam Long seekerId) {
        jobService.applyJob(jobId, seekerId);
    }
}

