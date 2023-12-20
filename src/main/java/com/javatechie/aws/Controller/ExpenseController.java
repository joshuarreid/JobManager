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
@CrossOrigin
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    private static final Logger logger = LogManager.getLogger(ExpenseController.class);

    @GetMapping("/expense")
    public ResponseEntity<Object> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/job/{jobId}/expenses")
    public ResponseEntity<Object> getAllExpensesByJobId(@PathVariable(value = "jobId") Long jobId) {
        return expenseService.getAllExpensesByJobId(jobId);
    }

    @PostMapping("/job/{jobId}/expenses")
    public ResponseEntity<Object> createExpense(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody Expense newExpense) {
        return expenseService.createExpense(jobId, newExpense);
    }


    @PutMapping("/expense/{id}")
    public ResponseEntity<Object> updateExpense(@PathVariable("id") long id, @RequestBody Expense updatedExpense) {
        return expenseService.updateExpense(id, updatedExpense);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Object> deleteExpense(@PathVariable("id") long id) {
        return expenseService.deleteExpense(id);
    }

    @DeleteMapping("/job/{jobId}/expenses")
    public ResponseEntity<Object> deleteAllExpensesOfJob(@PathVariable(value = "jobId") Long jobId) {
        return expenseService.deleteAllExpensesOfJob(jobId);
    }





}
