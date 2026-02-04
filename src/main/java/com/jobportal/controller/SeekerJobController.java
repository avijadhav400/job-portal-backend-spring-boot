package com.jobportal.controller;

import com.jobportal.dto.AppliedJobDTO;
import com.jobportal.dto.JobDTO;
import com.jobportal.security.CustomUserPrincipal;
import com.jobportal.service.SeekerJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seeker/jobs")
@RequiredArgsConstructor
public class SeekerJobController {

    private final SeekerJobService seekerJobService;

    // ✅ FIXED principal usage
    @GetMapping("/open")
    public List<JobDTO> openJobs(Authentication authentication) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        Long seekerId = principal.getUserId();
        return seekerJobService.viewOpenJobs(seekerId);
    }

    @PostMapping("/apply")
    public void apply(@RequestParam Long jobId, Authentication authentication) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        seekerJobService.applyJob(jobId, principal.getUserId());
    }

    @GetMapping("/applied")
    public List<AppliedJobDTO> viewAppliedJobs(Authentication authentication) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        return seekerJobService.viewAppliedJobs(principal.getUserId());
    }

    // ✅ FIXED: returns JobDTO with alreadyApplied
    @GetMapping("/search")
    public List<JobDTO> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxSalary,
            Authentication authentication
    ) {
        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        return seekerJobService.searchJobs(
                keyword,
                location,
                minExperience,
                minSalary,
                maxSalary,
                principal.getUserId()
        );
    }
}