package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Integer> {
}
