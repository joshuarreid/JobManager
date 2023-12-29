package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Job;
import com.javatechie.aws.common.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface JobRepository extends CrudRepository<Job, Long> {

    List<Job> findByStatus(Status status);

    List<Job> findByCustomerId(Long customerId);

    @Transactional
    void deleteByCustomerId(long id);
}
