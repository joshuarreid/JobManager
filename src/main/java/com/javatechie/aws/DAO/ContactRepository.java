package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ContactRepository extends CrudRepository<Contact,Long> {
    List<Contact> findByCompanyId(Long companyId);

    List<Contact> findByCustomerId(Long customerId);

    @Transactional
    void deleteByCompanyId(long id);

    @Transactional
    void deleteByCustomerId(long id);
}
