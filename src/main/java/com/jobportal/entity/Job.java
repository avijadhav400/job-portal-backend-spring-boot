package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String skills;

    private String location;

    private Integer experience;

    private Integer minSalary;

    private Integer maxSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
