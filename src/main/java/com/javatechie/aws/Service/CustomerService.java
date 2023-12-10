package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();

        customerRepository.findAll().forEach(customers::add);


        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    public ResponseEntity<Customer> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Customer with id = " + id));

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    public ResponseEntity<Customer> createCustomer(Customer newCustomer) {
        Customer customer = customerRepository.save(newCustomer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    public ResponseEntity<Customer> updateCustomer(long id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId " + id + "not found"));

        customer.setAddresses(updatedCustomer.getAddresses());
        customer.setName(updatedCustomer.getName());
        customer.setCreatedAt(updatedCustomer.getCreatedAt());
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> deleteCustomer(long id) {
        customerRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
