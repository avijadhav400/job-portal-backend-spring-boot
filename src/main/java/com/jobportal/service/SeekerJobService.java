package com.jobportal.service;

import com.jobportal.dto.AppliedJobDTO;
import com.jobportal.entity.*;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeekerJobService {

    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    // 1. View available jobs
    public List<Job> viewOpenJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN);
    }

    // 2. Apply job
    public String applyJob(Long jobId, Long seekerId) {

        if (applicationRepository.existsByJobIdAndSeekerId(jobId, seekerId)) {
            return "You have already applied for this job";
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User seeker = userRepository.findById(seekerId)
                .orElseThrow(() -> new RuntimeException("Seeker not found"));

        if (seeker.getRole() != Role.SEEKER) {
            throw new RuntimeException("Only seekers can apply");
        }

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setSeeker(seeker);

        applicationRepository.save(application);

        return "Job applied successfully";
    }



    public List<Job> searchJobs(
            String keyword,
            String location,
            Integer minExperience,
            Integer minSalary,
            Integer maxSalary
    ) {
        return jobRepository.searchJobs(
                JobStatus.OPEN,
                emptyToNull(keyword),
                emptyToNull(location),
                minExperience,
                minSalary,
                maxSalary
        );
    }

    private String emptyToNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    public List<AppliedJobDTO> viewAppliedJobs(Long seekerId) {

        List<JobApplication> applications =
                applicationRepository.findBySeekerId(seekerId);

        return applications.stream().map(app -> {
            Job job = app.getJob();

            AppliedJobDTO dto = new AppliedJobDTO();
            dto.setJobId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setLocation(job.getLocation());
            dto.setSkills(job.getSkills());
            dto.setExperience(job.getExperience());
            dto.setMinSalary(job.getMinSalary());
            dto.setMaxSalary(job.getMaxSalary());
            dto.setAppliedAt(app.getAppliedAt());

            return dto;
        }).toList();
    }

}

