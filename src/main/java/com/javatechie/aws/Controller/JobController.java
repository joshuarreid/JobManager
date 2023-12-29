package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Job;
import com.javatechie.aws.Service.JobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JobController {
    @Autowired
    JobService jobService;

    private static final Logger logger = LogManager.getLogger(JobController.class);

    @GetMapping("/job")
    public ResponseEntity<Object> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<Object> getJobById(@PathVariable(value = "id") Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("/customer/{customerId}/jobs")
    public ResponseEntity<Object> getAllJobsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return jobService.getAllJobsByCustomerId(customerId);
    }

    @PostMapping("/customer/{customerId}/jobs")
    public ResponseEntity<Object> createJob(@PathVariable(value = "customerId") Long customerId,
                                            @RequestBody Job newJob) {
        return jobService.createJob(customerId, newJob);
    }


    @PutMapping("/job/{id}")
    public ResponseEntity<Object> updateJob(@PathVariable("id") Long id, @RequestBody Job updatedJob) {
        return jobService.updateJob(id, updatedJob);
    }


    @DeleteMapping("/job/{id}")
    public ResponseEntity<Object> deleteJob(@PathVariable("id") Long id) {
        return jobService.deleteJob(id);
    }


    @DeleteMapping("/customer/{customerId}/jobs")
    public ResponseEntity<Object> deleteAllJobsOfCustomer(@PathVariable(value = "customerId") Long customerId) {
        return jobService.deleteAllJobsOfCustomer(customerId);
    }


}
