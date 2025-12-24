package com.fullstack.service;

import com.fullstack.model.Job;
import com.fullstack.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Optional<Job> findById(int jobId) {
        return jobRepository.findById(jobId);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> findByEmployeeId(int empId) {
        return jobRepository.findByEmployeeEmpId(empId);
    }

    @Override
    public Job update(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteById(int jobId) {
        jobRepository.deleteById(jobId);
    }
}
