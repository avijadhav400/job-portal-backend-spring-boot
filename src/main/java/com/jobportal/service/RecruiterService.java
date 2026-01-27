package com.jobportal.service;

import com.jobportal.dto.ApplicantResponseDTO;
import com.jobportal.repository.JobApplicationRepository;
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
}
