package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Customer;
import com.javatechie.aws.Service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @GetMapping("/customer")
    public ResponseEntity<Object> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/customer/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable(value = "id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer newCustomer) {
        return customerService.createCustomer(newCustomer);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") long id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer);
    }


    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") long id) {
        return customerService.deleteCustomer(id);
    }

}
