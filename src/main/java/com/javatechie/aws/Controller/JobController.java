package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.ContactRepository;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import com.javatechie.aws.Model.Job;
import com.javatechie.aws.Service.JobService;
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

    @GetMapping("/job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable(value = "id") Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("/customer/{customerId}/jobs")
    public ResponseEntity<List<Job>> getAllJobsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return jobService.getAllJobsByCustomerId(customerId);
    }

    @PostMapping("/customer/{customerId}/jobs")
    public ResponseEntity<Job> createJob(@PathVariable(value = "customerId") Long customerId,
                                         @RequestBody Job newJob) {
        return jobService.createJob(customerId, newJob);
    }


    @PutMapping("/job/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") Long id, @RequestBody Job updatedJob) {
        return jobService.updateJob(id, updatedJob);
    }


    @DeleteMapping("/job/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable("id") Long id) {
        return jobService.deleteJob(id);
    }


    @DeleteMapping("/customer/{customerId}/jobs")
    public ResponseEntity<List<Job>> deleteAllJobsOfCustomer(@PathVariable(value = "customerId") Long customerId) {
        return jobService.deleteAllJobsOfCustomer(customerId);
    }


}
