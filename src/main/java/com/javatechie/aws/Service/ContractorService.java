package com.javatechie.aws.Service;

import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.DAO.*;
import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractorService
{
    @Autowired
    LaborRepository laborRepository;

    @Autowired
    ContractorRepository contractorRepository;

    private static final Logger logger = LogManager.getLogger(ContractorService.class);


    public ResponseEntity<Object> getAllContractors() {
        Iterable<Contractor> contractors = new ArrayList<>();
        contractors = contractorRepository.findAll();
        logger.info(contractors);
        return ResponseHandler.generateResponse(contractors);
    }


    public ResponseEntity<Object> getAllContractorsByLaborId(Long laborId) {
        List<Contractor> contractors = new ArrayList<>();
        contractors = contractorRepository.findByLaborId(laborId);
        logger.info(contractors);
        return ResponseHandler.generateResponse(contractors);
    }


    public ResponseEntity<Object> createContractor(Long laborId, Contractor newContractor) {
        Contractor contractor = laborRepository.findById(laborId).map(labor -> {
            newContractor.setLabor(labor);
            return contractorRepository.save(newContractor);
        }).orElseThrow(() -> new ResourceNotFoundException("Labor does not exist: id=" + laborId));
        logger.info(contractor);
        return ResponseHandler.generateResponse(contractor);
    }



    public ResponseEntity<Object> updateContractor(long id, Contractor updatedContractor) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contractor does not exist: id=" + id));

        contractor.setName(updatedContractor.getName());
        contractor.setEmail(updatedContractor.getEmail());
        contractor.setPhone(updatedContractor.getPhone());
        contractor.setAddress(updatedContractor.getAddress());
        contractor.setPayRate(updatedContractor.getPayRate());
        contractor.setHoursWorked(updatedContractor.getHoursWorked());
        contractor.setStatus(updatedContractor.getStatus());
        contractor.setLabor(updatedContractor.getLabor());
        contractor.setImage(updatedContractor.getImage());
        contractor = contractorRepository.save(contractor);
        logger.info(contractor);
        return ResponseHandler.generateResponse(contractor);
    }


    public ResponseEntity<Object> deleteContractor(long id) {
        contractorRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteAllContractorsOfLabor(Long laborId) {
        contractorRepository.deleteByLaborId(laborId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


}