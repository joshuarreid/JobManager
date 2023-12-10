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
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        logger.info("Fetching All Customers...");
        return customerService.getAllCustomers();
    }


    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long id) {
        logger.info("Fetching CustomerId: " + id);
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        logger.info("Creating Customer...");
        logger.info(newCustomer.toString());
        return customerService.createCustomer(newCustomer);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer updatedCustomer) {
        logger.info("Updating CustomerId: " + id);
        return customerService.updateCustomer(id, updatedCustomer);
    }


    @DeleteMapping("/customer/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        logger.info("Deleting CustomerId: " + id);
        return customerService.deleteCustomer(id);
    }

}
