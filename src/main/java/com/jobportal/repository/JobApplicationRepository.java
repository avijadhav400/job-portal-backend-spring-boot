package com.jobportal.repository;

import com.jobportal.dto.ApplicantResponseDTO;
import com.jobportal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    boolean existsByJobIdAndSeekerId(Long jobId, Long seekerId);
//    List<JobApplication> findBySeekerId(Long seekerId);
    List<JobApplication> findByJobRecruiterId(Long recruiterId);

    @Query("""
    SELECT ja 
    FROM JobApplication ja
    WHERE ja.seeker.id = :seekerId
""")
    List<JobApplication> findBySeekerId(@Param("seekerId") Long seekerId);

    @Query("""
    SELECT new com.jobportal.dto.ApplicantResponseDTO(
        ja.id,
        j.id,
        j.title,
        s.id,
        s.fullName,
        s.email,
        ja.applicationStatus
    )
    FROM JobApplication ja
    JOIN ja.job j
    JOIN ja.seeker s
    WHERE j.recruiter.id = :recruiterId
    ORDER BY ja.id DESC
""")
    List<ApplicantResponseDTO> findApplicantsByRecruiterId(
            @Param("recruiterId") Long recruiterId
    );

    @Query("""
    SELECT ja
    FROM JobApplication ja
    JOIN ja.job j
    WHERE ja.id = :applicationId
      AND j.recruiter.id = :recruiterId
""")
    Optional<JobApplication> findByIdAndRecruiterId(
            @Param("applicationId") Long applicationId,
            @Param("recruiterId") Long recruiterId
    );
}
