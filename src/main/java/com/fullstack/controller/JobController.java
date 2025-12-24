package com.fullstack.controller;

import com.fullstack.dto.JobRequest;
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
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@Slf4j
public class JobController {

    @Autowired
    private IJobService jobService;

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/save/{empId}")
    public ResponseEntity<Job> save(@PathVariable int empId, @RequestBody JobRequest jobRequest) {
        Employee employee = employeeService.findById(empId)
                .orElseThrow(() -> new RecordNotFoundException("Employee not found with id " + empId));

        Job job = new Job();
        job.setJobName(jobRequest.getJobName());
        job.setEmployee(employee);
        return ResponseEntity.ok(jobService.save(job));
    }

    @GetMapping("/findbyid/{jobId}")
    public ResponseEntity<Optional<Job>> findById(@PathVariable int jobId) {
        return ResponseEntity.ok(jobService.findById(jobId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Job>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/findbyemployee/{empId}")
    public ResponseEntity<List<Job>> findByEmployeeId(@PathVariable int empId) {
        return ResponseEntity.ok(jobService.findByEmployeeId(empId));
    }

    @PutMapping("/update/{jobId}")
    public ResponseEntity<Job> update(@PathVariable int jobId, @RequestBody JobRequest jobRequest) {
        Job existingJob = jobService.findById(jobId)
                .orElseThrow(() -> new RecordNotFoundException("Job not found with id " + jobId));
        existingJob.setJobName(jobRequest.getJobName());
        return ResponseEntity.ok(jobService.save(existingJob));
    }

    @DeleteMapping("/deletebyid/{jobId}")
    public ResponseEntity<String> delete(@PathVariable int jobId) {
        jobService.deleteById(jobId);
        return ResponseEntity.ok("Job Deleted Successfully");
    }
}
