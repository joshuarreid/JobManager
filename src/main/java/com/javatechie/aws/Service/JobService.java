package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Job;
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

    public ResponseEntity<Iterable<Job>> getAllJobs() {
        try {
            Iterable<Job> jobs = new ArrayList<>();
            jobs = jobRepository.findAll();
            logger.info(jobs.toString());

            if (!jobs.iterator().hasNext()) {
                logger.info("No Jobs Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Jobs Successfully Fetched");
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Job> getJobById(Long id) {
        try {
            Job job = jobRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + id));
            logger.info(job.toString());
            logger.info("Job Successfully Fetched");
            return new ResponseEntity<>(job, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Job>> getAllJobsByCustomerId(Long customerId) {
        try {
            if (!customerRepository.existsById(customerId)) {
                throw new ResourceNotFoundException("Customer does not exist: id=" + customerId);
            }
            List<Job> jobs = new ArrayList<>();
            jobs = jobRepository.findByCustomerId(customerId);
            if (jobs.isEmpty()) {
                logger.info("No Jobs Found for CustomerId: " + customerId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(jobs.size() + " Jobs Successfully Found for CustomerId: " + customerId);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Job> createJob(Long customerId, Job newJob) {
        try {
            Job job = customerRepository.findById(customerId).map(customer -> {
                newJob.setCustomer(customer);
                return jobRepository.save(newJob);
            }).orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + customerId));
            logger.info(job);
            logger.info("Job Successfully Created for CustomerId: " + customerId);
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Job> updateJob(Long id, Job updatedJob) {
        try {
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
            logger.info("Job Successfully Updated");
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<HttpStatus> deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            logger.info("Job Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Job>> deleteAllJobsOfCustomer(Long customerId) {
        try {
            if (!customerRepository.existsById(customerId)) {
                throw new ResourceNotFoundException("Customer does not exist: id=" + customerId);
            }
            jobRepository.deleteByCustomerId(customerId);
            logger.info("All Jobs Successfully Deleted for CustomerId: " + customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
