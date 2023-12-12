package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.ExpenseRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.Order;
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

    public ResponseEntity<Iterable<Expense>> getAllExpenses() {
        try {
            Iterable<Expense> expenses = new ArrayList<>();
            expenses = expenseRepository.findAll();
            logger.info(expenses.toString());
            if (!expenses.iterator().hasNext()) {
                logger.info("No Expenses Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Expenses Successfully Fetched");
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Expense>> getAllExpensesByJobId(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            List<Expense> expenses = new ArrayList<>();
            expenses = expenseRepository.findByJobId(jobId);
            if (expenses.isEmpty()) {
                logger.info("No Expenses Found for JobId: " + jobId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(expenses.size() + " Expenses Successfully Found for JobId: " + jobId);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Expense> createExpense(Long jobId, Expense newExpense) {
        try {
            Expense expense = jobRepository.findById(jobId).map(job -> {
                newExpense.setJob(job);
                return expenseRepository.save(newExpense);
            }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
            logger.info(expense);
            logger.info("Expense Successfully Created for JobId: " + jobId);
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Expense> updateExpense(long id, Expense updatedExpense) {
        try {
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
            logger.info("Expense Successfully Updated");
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteExpense(long id) {
        try {
            expenseRepository.deleteById(id);
            logger.info("Expense Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Expense>> deleteAllExpensesOfJob(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            expenseRepository.deleteByJobId(jobId);
            logger.info("All Expenses Successfully Deleted for JobId: " + jobId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
