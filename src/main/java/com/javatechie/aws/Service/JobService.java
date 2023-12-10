package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class JobService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JobRepository jobRepository;


    public ResponseEntity<Job> getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Job with id = " + id));

        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    public ResponseEntity<List<Job>> getAllJobsByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Not found Customer with id = " + customerId);
        }
        List<Job> jobs = jobRepository.findByCustomerId(customerId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }


    public ResponseEntity<Job> createJob(Long customerId, Job newJob) {
        Job job = customerRepository.findById(customerId).map(customer -> {
            newJob.setCustomer(customer);
            return jobRepository.save(newJob);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Customer with id = " + customerId));

        return new ResponseEntity<>(job, HttpStatus.CREATED);
    }



    public ResponseEntity<Job> updateJob(Long id, Job updatedJob) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobId " + id + "not found"));

        if (updatedJob.getName() != null) job.setName(updatedJob.getName());
        if (updatedJob.getStatus() != null) job.setStatus(updatedJob.getStatus());
        if (updatedJob.getCustomer() != null) job.setCustomer(updatedJob.getCustomer());
        if (updatedJob.getEstimatedCost() != null) job.setEstimatedCost(updatedJob.getEstimatedCost());
        if (updatedJob.getTotalCost() != null) job.setTotalCost(updatedJob.getTotalCost());
        if (updatedJob.getCompletedAt() != null) job.setCompletedAt(updatedJob.getCompletedAt());
        return new ResponseEntity<>(jobRepository.save(job), HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> deleteJob(Long id) {
        jobRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    public ResponseEntity<List<Job>> deleteAllJobsOfCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Not found Customer with id = " + customerId);
        }

        jobRepository.deleteByCustomerId(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //    @PutMapping("/customer/{customerId}/jobs")
//    public ResponseEntity<Job> addJobToCustomer(@PathVariable(value = "customerId") Long customerId,
//                                                        @RequestBody Job newJob) {
//        Job job = jobRepository.findById(newJob.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("JobId " + newJob.getId() + "not found"));
//
//
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("CustomerId " + customerId + "not found"));
//        job.setCustomer(customer);
//        return new ResponseEntity<>(jobRepository.save(job), HttpStatus.OK);
//    }






}
