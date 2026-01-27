package com.jobportal.controller;

import com.jobportal.dto.AppliedJobDTO;
import com.jobportal.entity.Job;
import com.jobportal.service.SeekerJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seeker/jobs")
@RequiredArgsConstructor
public class SeekerJobController {

    private final SeekerJobService seekerJobService;

    // 1. View all open jobs
    @GetMapping("/open")
    public List<Job> openJobs() {
        return seekerJobService.viewOpenJobs();
    }

    // 2. Apply job
    @PostMapping("/apply")
    public String applyJob(@RequestParam Long jobId,
                           @RequestParam Long seekerId) {
        return seekerJobService.applyJob(jobId, seekerId);
    }

    @GetMapping("/applied/{seekerId}")
    public List<AppliedJobDTO> viewAppliedJobs(
            @PathVariable Long seekerId) {
        return seekerJobService.viewAppliedJobs(seekerId);
    }

    @GetMapping("/search")
    public List<Job> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxSalary
    ) {
        return seekerJobService.searchJobs(
                keyword,
                location,
                minExperience,
                minSalary,
                maxSalary
        );
    }
}

