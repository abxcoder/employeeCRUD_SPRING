package com.fullstack.controller;

import com.fullstack.dto.JobRequest;
import com.fullstack.dto.JobResponse;
import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.model.Employee;
import com.fullstack.model.Job;
import com.fullstack.service.IEmployeeService;
import com.fullstack.service.IJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jobs")
@Slf4j
public class JobController {

    @Autowired
    private IJobService jobService;

    @Autowired
    private IEmployeeService employeeService;

    // Convert Job entity to JobResponse DTO
    private JobResponse toJobResponse(Job job) {
        return new JobResponse(
                job.getJobId(),
                job.getJobName(),
                job.getEmployee().getEmpId(),
                job.getEmployee().getEmpName()
        );
    }

    @PostMapping("/save/{empId}")
    public ResponseEntity<JobResponse> save(@PathVariable int empId, @RequestBody JobRequest jobRequest) {
        Employee employee = employeeService.findById(empId)
                .orElseThrow(() -> new RecordNotFoundException("Employee not found with id " + empId));

        Job job = new Job();
        job.setJobName(jobRequest.getJobName());
        job.setEmployee(employee);
        Job savedJob = jobService.save(job);
        return ResponseEntity.ok(toJobResponse(savedJob));
    }

    @GetMapping("/findbyid/{jobId}")
    public ResponseEntity<JobResponse> findById(@PathVariable int jobId) {
        Job job = jobService.findById(jobId)
                .orElseThrow(() -> new RecordNotFoundException("Job not found with id " + jobId));
        return ResponseEntity.ok(toJobResponse(job));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<JobResponse>> findAll() {
        List<JobResponse> jobs = jobService.findAll().stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/findbyemployee/{empId}")
    public ResponseEntity<List<JobResponse>> findByEmployeeId(@PathVariable int empId) {
        List<JobResponse> jobs = jobService.findByEmployeeId(empId).stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/update/{jobId}")
    public ResponseEntity<JobResponse> update(@PathVariable int jobId, @RequestBody JobRequest jobRequest) {
        Job existingJob = jobService.findById(jobId)
                .orElseThrow(() -> new RecordNotFoundException("Job not found with id " + jobId));
        existingJob.setJobName(jobRequest.getJobName());
        Job updatedJob = jobService.save(existingJob);
        return ResponseEntity.ok(toJobResponse(updatedJob));
    }

    @DeleteMapping("/deletebyid/{jobId}")
    public ResponseEntity<String> delete(@PathVariable int jobId) {
        jobService.deleteById(jobId);
        return ResponseEntity.ok("Job Deleted Successfully");
    }
}
