package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.*;
import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ContractorService
{
    @Autowired
    LaborRepository laborRepository;

    @Autowired
    ContractorRepository contractorRepository;


    public ResponseEntity<Iterable<Contractor>> getAllContractors() {
        return new ResponseEntity<>(contractorRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<List<Contractor>> getAllContractorsByJobId(Long laborId) {
        if (!laborRepository.existsById(laborId)) {
            throw new ResourceNotFoundException("Not found Labor with id = " + laborId);
        }
        List<Contractor> contractors = contractorRepository.findByLaborId(laborId);
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }


    public ResponseEntity<Contractor> createContractor(Long laborId, Contractor newContractor) {
        Contractor contractor = laborRepository.findById(laborId).map(labor -> {
            newContractor.setLabor(labor);
            return contractorRepository.save(newContractor);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Labor with id = " + laborId));

        return new ResponseEntity<>(contractor, HttpStatus.CREATED);
    }



    public ResponseEntity<Contractor> updateContractor(long id, Contractor updatedContractor) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContractorId " + id + "not found"));

        if (updatedContractor.getName() != null) contractor.setName(updatedContractor.getName());
        if (updatedContractor.getEmail() != null) contractor.setEmail(updatedContractor.getEmail());
        if ((Integer) updatedContractor.getPhone() != null) contractor.setPhone(updatedContractor.getPhone());
        if (updatedContractor.getAddress() != null) contractor.setAddress(updatedContractor.getAddress());
        if (updatedContractor.getPayRate() != null) contractor.setPayRate(updatedContractor.getPayRate());
        if (updatedContractor.getHoursWorked() != null) contractor.setHoursWorked(updatedContractor.getHoursWorked());
        if (updatedContractor.getStatus() != null) contractor.setStatus(updatedContractor.getStatus());
        if (updatedContractor.getLabor() != null) contractor.setLabor(updatedContractor.getLabor());

        return new ResponseEntity<>(contractorRepository.save(contractor), HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> deleteContractor(long id) {
        contractorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Order>> deleteAllContractorsOfLabor(Long laborId) {
        if (!laborRepository.existsById(laborId)) {
            throw new ResourceNotFoundException("Not found Labor with id = " + laborId);
        }

        contractorRepository.deleteByLaborId(laborId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}