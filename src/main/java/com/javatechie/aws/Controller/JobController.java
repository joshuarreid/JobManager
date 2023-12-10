package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Customer;
import com.javatechie.aws.Model.Job;
import com.javatechie.aws.Service.JobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobController {
    @Autowired
    JobService jobService;

    private static final Logger logger = LogManager.getLogger(JobController.class);

    @GetMapping("/job")
    public ResponseEntity<Iterable<Job>> getAllJobs() {
        logger.info("Fetching All Jobs...");
        return jobService.getAllJobs();
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable(value = "id") Long id) {
        logger.info("Fetching All Jobs...");
        return jobService.getJobById(id);
    }

    @GetMapping("/customer/{customerId}/jobs")
    public ResponseEntity<List<Job>> getAllJobsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        logger.info("Fetching Jobs for CustomerId: " + customerId);
        return jobService.getAllJobsByCustomerId(customerId);
    }

    @PostMapping("/customer/{customerId}/jobs")
    public ResponseEntity<Job> createJob(@PathVariable(value = "customerId") Long customerId,
                                         @RequestBody Job newJob) {
        logger.info("Creating Job...");
        logger.info(newJob.toString());
        return jobService.createJob(customerId, newJob);
    }


    @PutMapping("/job/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") Long id, @RequestBody Job updatedJob) {
        logger.info("Updating JobId: " + id);
        return jobService.updateJob(id, updatedJob);
    }


    @DeleteMapping("/job/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable("id") Long id) {
        logger.info("Deleting JobId: " + id);
        return jobService.deleteJob(id);
    }


    @DeleteMapping("/customer/{customerId}/jobs")
    public ResponseEntity<List<Job>> deleteAllJobsOfCustomer(@PathVariable(value = "customerId") Long customerId) {
        logger.info("Deleting All Jobs for CustomerId: " + customerId);
        return jobService.deleteAllJobsOfCustomer(customerId);
    }


}
