package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
}
