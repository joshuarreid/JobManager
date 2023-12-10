package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.ExpenseRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    public ResponseEntity<Iterable<Expense>> getAllExpenses() {
        return new ResponseEntity<>(expenseRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Expense>> getAllExpensesByJobId(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Not found Job with id = " + jobId);
        }
        List<Expense> expenses = expenseRepository.findByJobId(jobId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    public ResponseEntity<Expense> createExpense(Long jobId, Expense newExpense) {
        Expense expense = jobRepository.findById(jobId).map(job -> {
            newExpense.setJob(job);
            return expenseRepository.save(newExpense);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Job with id = " + jobId));

        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }


    public ResponseEntity<Expense> updateExpense(long id, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseId " + id + "not found"));

        if (updatedExpense.getDescription() != null) expense.setDescription(updatedExpense.getDescription());
        if (updatedExpense.getCost() != null) expense.setCost(updatedExpense.getCost());
        if (updatedExpense.getDate() != null) expense.setDate(updatedExpense.getDate());
        if (updatedExpense.getType() != null) expense.setType(updatedExpense.getType());
        if (updatedExpense.getJob() != null) expense.setJob(updatedExpense.getJob());
        return new ResponseEntity<>(expenseRepository.save(expense), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteExpense(long id) {
        expenseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Order>> deleteAllExpensesOfJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Not found Job with id = " + jobId);
        }

        expenseRepository.deleteByJobId(jobId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
