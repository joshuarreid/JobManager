package com.javatechie.aws.Service;

import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Job;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JobRepository jobRepository;

    private static final Logger logger = LogManager.getLogger(JobService.class);

    public ResponseEntity<Object> getAllJobs() {
        Iterable<Job> jobs = new ArrayList<>();
        jobs = jobRepository.findAll();
        logger.info(jobs);
        return ResponseHandler.generateResponse(jobs);
    }

    public ResponseEntity<Object> getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + id));
        logger.info(job);
        return ResponseHandler.generateResponse(job);
    }

    public ResponseEntity<Object> getAllJobsByCustomerId(Long customerId) {
        List<Job> jobs = new ArrayList<>();
        jobs = jobRepository.findByCustomerId(customerId);
        logger.info(jobs);
        return ResponseHandler.generateResponse(jobs);
    }


    public ResponseEntity<Object> createJob(Long customerId, Job newJob) {
        Job job = customerRepository.findById(customerId).map(customer -> {
            newJob.setCustomer(customer);
            return jobRepository.save(newJob);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + customerId));
        logger.info(job);
        return ResponseHandler.generateResponse(job);
    }



    public ResponseEntity<Object> updateJob(Long id, Job updatedJob) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + id));

        job.setName(updatedJob.getName());
        job.setStatus(updatedJob.getStatus());
        job.setCustomer(updatedJob.getCustomer());
        job.setEstimatedCost(updatedJob.getEstimatedCost());
        job.setTotalCost(updatedJob.getTotalCost());
        job.setCompletedAt(updatedJob.getCompletedAt());
        jobRepository.save(job);
        logger.info(job);
        return ResponseHandler.generateResponse(job);
    }


    public ResponseEntity<Object> deleteJob(Long id) {
        jobRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


    public ResponseEntity<Object> deleteAllJobsOfCustomer(Long customerId) {
        jobRepository.deleteByCustomerId(customerId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }
}
