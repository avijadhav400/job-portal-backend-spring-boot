package com.jobportal.service;

import com.jobportal.dto.UpdateApplicationStatusRequest;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.JobApplication;
import com.jobportal.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    @Transactional
    public void updateApplicationStatus(
            Long recruiterId,
            Long applicationId,
            ApplicationStatus newStatus
    ) {
        JobApplication application = jobApplicationRepository
                .findByIdAndRecruiterId(applicationId, recruiterId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found or access denied")
                );

        application.setApplicationStatus(newStatus);
        // save not required (managed entity)
    }
}
