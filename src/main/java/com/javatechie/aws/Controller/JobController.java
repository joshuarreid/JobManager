package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Job;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Service.JobService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @GetMapping
    public List<Job> findJobs() {
        return jobService.findJobs();
    }


    @SneakyThrows
    @GetMapping("/{id}")
    public Job findJob(@PathVariable int id) throws Exception {
        Job job = jobService.findJob(id);
        return job;
    }

    @SneakyThrows
    @GetMapping("update/{id}")
    public Job updateJob(@PathVariable int id, Job updatedJob) throws Exception {
        Job job = jobService.updateJob(id, updatedJob);
        return job;
    }
}
