package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Customer;
import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);

    public ResponseEntity<Object> getAllCustomers() {
        Iterable<Customer> customers = new ArrayList<>();
        customers = customerRepository.findAll();
        logger.info(customers);
        return ResponseHandler.generateResponse(customers);
    }


    public ResponseEntity<Object> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + id));
        logger.info(customer);
        return ResponseHandler.generateResponse(customer);
    }

    public ResponseEntity<Object> createCustomer(Customer newCustomer) {
        Customer customer = customerRepository.save(newCustomer);
        logger.info(customer);
        return ResponseHandler.generateResponse(customer);
    }

    public ResponseEntity<Object> updateCustomer(long id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + id));

        customer.setAddresses(updatedCustomer.getAddresses());
        customer.setName(updatedCustomer.getName());
        customer.setImage(updatedCustomer.getImage());
        customer = customerRepository.save(customer);
        logger.info(customer);
        return ResponseHandler.generateResponse(customer);
    }


    public ResponseEntity<Object> deleteCustomer(long id) {
        customerRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }
}
