package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }


    public Customer findCustomer(@PathVariable int id) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("Customer not found"));
        return customer;
    }

    public Customer updateCustomer(@PathVariable int id, Customer updatedCustomer) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("Customer not found"));
        customer.setName(updatedCustomer.getName());
        customer.setContactId(updatedCustomer.getContactId());
        customer.setAddresses(updatedCustomer.getAddresses());
        customer.setCreatedAt(updatedCustomer.getCreatedAt());
        customerRepository.save(customer);
        return customer;
    }
}
