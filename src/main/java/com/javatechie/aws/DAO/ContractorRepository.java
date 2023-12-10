package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Labor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ContractorRepository extends CrudRepository<Contractor,Long> {

    List<Contractor> findByLaborId(Long laborId);

    @Transactional
    void deleteByLaborId(long id);
}
