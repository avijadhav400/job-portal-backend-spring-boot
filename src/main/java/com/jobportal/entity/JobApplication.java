package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "job_applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "seeker_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "seeker_id", nullable = false)
    private User seeker;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false)
    private ApplicationStatus applicationStatus;

    private LocalDateTime appliedAt;

    @PrePersist
    public void onApply() {
        this.appliedAt = LocalDateTime.now();
        this.applicationStatus = ApplicationStatus.APPLIED; // ✅ default
    }
}
