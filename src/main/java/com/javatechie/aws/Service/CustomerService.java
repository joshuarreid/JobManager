package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Customer;
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

    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        try {
            Iterable<Customer> customers = new ArrayList<>();
            customers = customerRepository.findAll();
            logger.info(customers.toString());
            if (!customers.iterator().hasNext()) {
                logger.info("No Customers Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Customers Successfully Fetched");
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<Customer> getCustomerById(Long id) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + id));
            logger.info(customer.toString());
            logger.info("Customer Successfully Fetched");
            return new ResponseEntity<>(customer, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Customer> createCustomer(Customer newCustomer) {
        try {
            Customer customer = customerRepository.save(newCustomer);
            logger.info(customer);
            logger.info("Customer Successfully Created");
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Customer> updateCustomer(long id, Customer updatedCustomer) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + id));

            customer.setAddresses(updatedCustomer.getAddresses());
            customer.setName(updatedCustomer.getName());
            customer = customerRepository.save(customer);
            logger.info(customer);
            logger.info("Customer Successfully Updated");
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<HttpStatus> deleteCustomer(long id) {
        try {
            customerRepository.deleteById(id);
            logger.info("Customer Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
