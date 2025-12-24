package com.fullstack.service;

import com.fullstack.model.Job;

import java.util.List;
import java.util.Optional;

public interface IJobService {
    Job save(Job job);

    Optional<Job> findById(int jobId);

    List<Job> findAll();

    List<Job> findByEmployeeId(int empId);

    Job update(Job job);

    void deleteById(int jobId);
}
