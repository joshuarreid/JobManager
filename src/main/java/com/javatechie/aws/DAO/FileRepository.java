package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface FileRepository extends CrudRepository<File,Long> {

    List<File> findByJobId(Long jobId);

    @Transactional
    void deleteByJobId(long id);
}
