package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.ExpenseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    private static final Logger logger = LogManager.getLogger(ExpenseController.class);

    @GetMapping("/expense")
    public ResponseEntity<Iterable<Expense>> getAllExpenses() {
        logger.info("Fetching All Expenses...");
        return expenseService.getAllExpenses();
    }

    @GetMapping("/job/{jobId}/expenses")
    public ResponseEntity<List<Expense>> getAllExpensesByJobId(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Fetching Expenses for jobId: " + jobId);
        return expenseService.getAllExpensesByJobId(jobId);
    }

    @PostMapping("/job/{jobId}/expenses")
    public ResponseEntity<Expense> createExpense(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody Expense newExpense) {
        logger.info("Creating Expense...");
        logger.info(newExpense.toString());
        return expenseService.createExpense(jobId, newExpense);
    }


    @PutMapping("/expense/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") long id, @RequestBody Expense updatedExpense) {
        logger.info("Updating ExpenseId: " + id);
        return expenseService.updateExpense(id, updatedExpense);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<HttpStatus> deleteExpense(@PathVariable("id") long id) {
        logger.info("Deleting ExpenseId: " + id);
        return expenseService.deleteExpense(id);
    }

    @DeleteMapping("/job/{jobId}/expenses")
    public ResponseEntity<List<Expense>> deleteAllExpensesOfJob(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Deleting All Expenses for JobId: " + jobId);
        return expenseService.deleteAllExpensesOfJob(jobId);
    }





}
