package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.ExpenseService;
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

    @GetMapping("/expense")
    public ResponseEntity<Iterable<Expense>> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/job/{jobId}/expenses")
    public ResponseEntity<List<Expense>> getAllExpensesByJobId(@PathVariable(value = "jobId") Long jobId) {
        return expenseService.getAllExpensesByJobId(jobId);
    }

    @PostMapping("/job/{jobId}/expenses")
    public ResponseEntity<Expense> createExpense(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody Expense newExpense) {
        return expenseService.createExpense(jobId, newExpense);
    }


    @PutMapping("/expense/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") long id, @RequestBody Expense updatedExpense) {
        return expenseService.updateExpense(id, updatedExpense);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<HttpStatus> deleteExpense(@PathVariable("id") long id) {
        return expenseService.deleteExpense(id);
    }

    @DeleteMapping("/job/{jobId}/expenses")
    public ResponseEntity<List<Order>> deleteAllExpensesOfJob(@PathVariable(value = "jobId") Long jobId) {
        return expenseService.deleteAllExpensesOfJob(jobId);
    }





}
