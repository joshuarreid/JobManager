package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {

}
