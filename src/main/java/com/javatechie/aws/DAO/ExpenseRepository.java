package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.Labor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ExpenseRepository extends CrudRepository<Expense,Long> {

    List<Expense> findByJobId(Long jobId);

    @Transactional
    void deleteByJobId(long id);
}
