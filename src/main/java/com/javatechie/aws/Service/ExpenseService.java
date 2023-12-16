package com.javatechie.aws.Service;

import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.DAO.ExpenseRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Expense;
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
public class ExpenseService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    private static final Logger logger = LogManager.getLogger(ExpenseService.class);

    public ResponseEntity<Object> getAllExpenses() {
        Iterable<Expense> expenses = new ArrayList<>();
        expenses = expenseRepository.findAll();
        logger.info(expenses);
        return ResponseHandler.generateResponse(expenses);
    }

    public ResponseEntity<Object> getAllExpensesByJobId(Long jobId) {
        List<Expense> expenses = new ArrayList<>();
        expenses = expenseRepository.findByJobId(jobId);
        logger.info(expenses);
        return ResponseHandler.generateResponse(expenses);
    }

    public ResponseEntity<Object> createExpense(Long jobId, Expense newExpense) {
        Expense expense = jobRepository.findById(jobId).map(job -> {
            newExpense.setJob(job);
            return expenseRepository.save(newExpense);
        }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
        logger.info(expense);
        return ResponseHandler.generateResponse(expense);
    }


    public ResponseEntity<Object> updateExpense(long id, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense does not exist: id=" + id));

        expense.setDescription(updatedExpense.getDescription());
        expense.setCost(updatedExpense.getCost());
        expense.setDate(updatedExpense.getDate());
        expense.setType(updatedExpense.getType());
        expense.setJob(updatedExpense.getJob());
        expense.setImage(updatedExpense.getImage());
        expenseRepository.save(expense);
        logger.info(expense);
        return ResponseHandler.generateResponse(expense);
    }

    public ResponseEntity<Object> deleteExpense(long id) {
        expenseRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteAllExpensesOfJob(Long jobId) {
        expenseRepository.deleteByJobId(jobId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }





}
