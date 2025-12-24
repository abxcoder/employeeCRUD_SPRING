package com.fullstack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "JOBS")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    private String jobName;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;
}
