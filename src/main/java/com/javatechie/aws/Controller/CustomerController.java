package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.ContactRepository;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ContactRepository contactRepository;


    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Customer with id = " + id));

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        Customer customer = customerRepository.save(newCustomer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId " + id + "not found"));

        customer.setAddresses(updatedCustomer.getAddresses());
        customer.setName(updatedCustomer.getName());
        customer.setCreatedAt(updatedCustomer.getCreatedAt());
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
    }


    @DeleteMapping("/customer/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        customerRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
