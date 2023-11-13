package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Customer;
import com.javatechie.aws.Model.Job;
import com.javatechie.aws.Service.CustomerService;
import com.javatechie.aws.Service.JobService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> findCustomers() {
        return customerService.findCustomers();
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public Customer findCustomer(@PathVariable int id) throws Exception {
        Customer customer = customerService.findCustomer(id);
        return customer;
    }

    @SneakyThrows
    @GetMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable int id, Customer updatedCustomer) throws Exception {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return customer;
    }
}
