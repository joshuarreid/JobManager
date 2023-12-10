package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.ContractorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractorController {

    @Autowired
    ContractorService contractorService;

    private static final Logger logger = LogManager.getLogger(ContractorController.class);

    @GetMapping("/contractor")
    public ResponseEntity<Iterable<Contractor>> getAllContractors() {
        logger.info("Fetching All Contractors...");
        return contractorService.getAllContractors();
    }

    @GetMapping("/labor/{laborId}/contractors")
    public ResponseEntity<List<Contractor>> getAllContractorsByJobId(@PathVariable(value = "laborId") Long laborId) {
        logger.info("Fetching Contractor for laborId: " + laborId);
        return contractorService.getAllContractorsByLaborId(laborId);
    }

    @PostMapping("/labor/{laborId}/contractors")
    public ResponseEntity<Contractor> createContractor(@PathVariable(value = "laborId") Long laborId,
                                                       @RequestBody Contractor newContractor) {
        logger.info("Creating Contractor...");
        logger.info(newContractor.toString());
        return contractorService.createContractor(laborId, newContractor);
    }


    @PutMapping("/contractor/{id}")
    public ResponseEntity<Contractor> updateContractor(@PathVariable("id") Long id, @RequestBody Contractor updatedContractor) {
        logger.info("Updating ContractorId: " + id);
        return contractorService.updateContractor(id, updatedContractor);
    }

    @DeleteMapping("/contractor/{id}")
    public ResponseEntity<HttpStatus> deleteContractor(@PathVariable("id") Long id) {
        logger.info("Deleting ContractorId: " + id);
        return contractorService.deleteContractor(id);
    }

    @DeleteMapping("/labor/{laborId}/contractors")
    public ResponseEntity<List<Order>> deleteAllContractorsOfLabor(@PathVariable(value = "laborId") Long laborId) {
        logger.info("Deleting All Contractors for LaborId: " + laborId);
        return contractorService.deleteAllContractorsOfLabor(laborId);
    }





}
