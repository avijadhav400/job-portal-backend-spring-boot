package com.jobportal.service;

import com.jobportal.dto.ApplicantResponseDTO;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.JobApplication;
import com.jobportal.repository.JobApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruiterService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<ApplicantResponseDTO> viewApplicants(Long recruiterId) {
        return jobApplicationRepository.findApplicantsByRecruiterId(recruiterId);
    }

    @Transactional
    public void updateApplicationStatus(
            Long recruiterId,
            Long applicationId,
            ApplicationStatus status
    ) {
        JobApplication application =
                jobApplicationRepository
                        .findByIdAndRecruiterId(applicationId, recruiterId)
                        .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setApplicationStatus(status);
    }
}
