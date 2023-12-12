package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.*;
import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Order;
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


    public ResponseEntity<Iterable<Contractor>> getAllContractors() {
        try {
            Iterable<Contractor> contractors = new ArrayList<>();
            contractors = contractorRepository.findAll();
            logger.info(contractors.toString());
            if (!contractors.iterator().hasNext()) {
                logger.info("No Contractors Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Contractors Successfully Fetched");
            return new ResponseEntity<>(contractors, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<List<Contractor>> getAllContractorsByLaborId(Long laborId) {
        try {
            if (!laborRepository.existsById(laborId)) {
                throw new ResourceNotFoundException("Labor does not exist: id=" + laborId);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        try {
            List<Contractor> contractors = new ArrayList<>();
            contractors = contractorRepository.findByLaborId(laborId);
            if (contractors.isEmpty()) {
                logger.info("No Contractors Found for LaborId: " + laborId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(contractors.size() + " Contractors Successfully Found for LaborId: " + laborId);
            return new ResponseEntity<>(contractors, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Contractor> createContractor(Long laborId, Contractor newContractor) {
        try {
            if (!laborRepository.existsById(laborId)) {
                throw new ResourceNotFoundException("Labor does not exist: id=" + laborId);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
            Contractor contractor = laborRepository.findById(laborId).map(labor -> {
                newContractor.setLabor(labor);
                return contractorRepository.save(newContractor);
            }).orElseThrow(() -> new ResourceNotFoundException("Labor does not exist: id=" + laborId));
            logger.info(contractor);
            logger.info("Contractor Successfully Created for LaborId: " + laborId);
            return new ResponseEntity<>(contractor, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Contractor> updateContractor(long id, Contractor updatedContractor) {
        try {
            if (!contractorRepository.existsById(id)) {
                throw new ResourceNotFoundException("Contractor does not exist: id=" + id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        try {
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
            logger.info("Contractor Successfully Updated");
            return new ResponseEntity<>(contractor, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    public ResponseEntity<HttpStatus> deleteContractor(long id) {
        try {
            if (!contractorRepository.existsById(id)) {
                throw new ResourceNotFoundException("Contractor does not exist: id=" + id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
            contractorRepository.deleteById(id);
            logger.info("Contractor Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllContractorsOfLabor(Long laborId) {
        try {
            if (!laborRepository.existsById(laborId)) {
                throw new ResourceNotFoundException("Labor does not exist: id=" + laborId);
            }
        } catch(Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {

            contractorRepository.deleteByLaborId(laborId);
            logger.info("All Contacts Successfully Deleted for LaborId: " + laborId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}