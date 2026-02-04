package com.jobportal.service;

import com.jobportal.dto.AppliedJobDTO;
import com.jobportal.dto.JobDTO;
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

    // ✅ FIXED: seekerId added
    public List<JobDTO> viewOpenJobs(Long seekerId) {

        List<Job> jobs = jobRepository.findByStatus(JobStatus.OPEN);

        return jobs.stream().map(job -> {

            JobDTO dto = new JobDTO();
            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setLocation(job.getLocation());
            dto.setExperience(job.getExperience());
            dto.setMinSalary(job.getMinSalary());
            dto.setMaxSalary(job.getMaxSalary());
            dto.setSkills(job.getSkills());
            dto.setStatus(job.getStatus().name());

            // ✅ THIS NOW WORKS CORRECTLY
            dto.setAlreadyApplied(
                    applicationRepository.existsByJobIdAndSeekerId(
                            job.getId(),
                            seekerId
                    )
            );

            return dto;

        }).toList();
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

    // ✅ FIXED: return JobDTO instead of Job
    public List<JobDTO> searchJobs(
            String keyword,
            String location,
            Integer minExperience,
            Integer minSalary,
            Integer maxSalary,
            Long seekerId
    ) {
        return jobRepository.searchJobs(
                JobStatus.OPEN,
                emptyToNull(keyword),
                emptyToNull(location),
                minExperience,
                minSalary,
                maxSalary
        ).stream().map(job -> {

            JobDTO dto = new JobDTO();
            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setLocation(job.getLocation());
            dto.setExperience(job.getExperience());
            dto.setMinSalary(job.getMinSalary());
            dto.setMaxSalary(job.getMaxSalary());
            dto.setSkills(job.getSkills());
            dto.setStatus(job.getStatus().name());

            dto.setAlreadyApplied(
                    applicationRepository.existsByJobIdAndSeekerId(
                            job.getId(),
                            seekerId
                    )
            );

            return dto;
        }).toList();
    }

    private String emptyToNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    public List<AppliedJobDTO> viewAppliedJobs(Long seekerId) {

        return applicationRepository.findBySeekerId(seekerId)
                .stream()
                .map(app -> {
                    Job job = app.getJob();

                    AppliedJobDTO dto = new AppliedJobDTO();
                    dto.setJobId(job.getId());

                    // ✅ FIXED FIELD NAMES
                    dto.setJobTitle(job.getTitle());
                    dto.setApplicationStatus(app.getApplicationStatus());
                    dto.setAppliedAt(app.getAppliedAt());

                    return dto;
                })
                .toList();
    }
}