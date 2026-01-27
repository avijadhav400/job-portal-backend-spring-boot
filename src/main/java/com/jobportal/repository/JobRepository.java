package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByRecruiterId(Long recruiterId);

    List<Job> findByStatus(JobStatus status);

    @Query("""
        SELECT j FROM Job j
        WHERE j.status = :status
        AND (:keyword IS NULL OR
             LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND (:location IS NULL OR LOWER(j.location) = LOWER(:location))
        AND (:minExperience IS NULL OR j.experience >= :minExperience)
        AND (:minSalary IS NULL OR j.minSalary >= :minSalary)
        AND (:maxSalary IS NULL OR j.maxSalary <= :maxSalary)
    """)
    List<Job> searchJobs(
            @Param("status") JobStatus status,
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("minExperience") Integer minExperience,
            @Param("minSalary") Integer minSalary,
            @Param("maxSalary") Integer maxSalary
    );
}
