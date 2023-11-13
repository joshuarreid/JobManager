package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;


    public Job saveJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    public List<Job> findJobs() {
        return jobRepository.findAll();
    }


    public Job findJob(@PathVariable int id) throws Exception {
        Job job = jobRepository.findById(id).orElseThrow(() -> new Exception("Job not found"));
        return job;
    }

    public Job updateJob(@PathVariable int id, Job updatedJob) throws Exception {
        Job job = jobRepository.findById(id).orElseThrow(() -> new Exception("Job not found"));
        job.setName(updatedJob.getName());
        job.setCustomerId(updatedJob.getCustomerId());
        job.setOrderIds(updatedJob.getOrderIds());
        job.setEstimatedCost(updatedJob.getEstimatedCost());
        job.setTotalCost(updatedJob.getTotalCost());
        job.setStatus(updatedJob.getStatus());
        jobRepository.save(job);
        return job;
    }
}
