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
@CrossOrigin
@RequestMapping("/api")
public class ContractorController {

    @Autowired
    ContractorService contractorService;

    private static final Logger logger = LogManager.getLogger(ContractorController.class);

    @GetMapping("/contractor")
    public ResponseEntity<Object> getAllContractors() {
        return contractorService.getAllContractors();
    }

    @GetMapping("/labor/{laborId}/contractors")
    public ResponseEntity<Object> getAllContractorsByLaborId(@PathVariable(value = "laborId") Long laborId) {
        return contractorService.getAllContractorsByLaborId(laborId);
    }

    @PostMapping("/labor/{laborId}/contractors")
    public ResponseEntity<Object> createContractor(@PathVariable(value = "laborId") Long laborId,
                                                       @RequestBody Contractor newContractor) {
        return contractorService.createContractor(laborId, newContractor);
    }


    @PutMapping("/contractor/{id}")
    public ResponseEntity<Object> updateContractor(@PathVariable("id") Long id, @RequestBody Contractor updatedContractor) {
        return contractorService.updateContractor(id, updatedContractor);
    }

    @DeleteMapping("/contractor/{id}")
    public ResponseEntity<Object> deleteContractor(@PathVariable("id") Long id) {
        return contractorService.deleteContractor(id);
    }

    @DeleteMapping("/labor/{laborId}/contractors")
    public ResponseEntity<Object> deleteAllContractorsOfLabor(@PathVariable(value = "laborId") Long laborId) {
        return contractorService.deleteAllContractorsOfLabor(laborId);
    }





}
