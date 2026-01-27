package com.jobportal.service;

import com.jobportal.dto.CreateJobRequest;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.entity.JobStatus;
import com.jobportal.entity.User;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository appRepo;
    @Autowired
    private UserRepository userRepository;

    public void applyJob(Long jobId, Long seekerId) {

        if (appRepo.existsByJobIdAndSeekerId(jobId, seekerId)) {
            throw new RuntimeException("Already applied for this job");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User seeker = userRepository.findById(seekerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setSeeker(seeker);

        appRepo.save(application);
    }

    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job createJob(CreateJobRequest request, Long recruiterId) {


        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSkills(request.getSkills());
        job.setLocation(request.getLocation());
        job.setExperience(request.getExperience());
        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());
        job.setStatus(JobStatus.OPEN);
        job.setRecruiter(recruiter);

        return jobRepository.save(job);
    }

    public List<Job> getRecruiterJobs(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }

    public Job closeJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus(JobStatus.CLOSED);
        return jobRepository.save(job);
    }
}

