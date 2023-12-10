package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Contractor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.ContractorService;
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

    @GetMapping("/contractor")
    public ResponseEntity<Iterable<Contractor>> getAllContractors() {
        return contractorService.getAllContractors();
    }

    @GetMapping("/labor/{laborId}/contractors")
    public ResponseEntity<List<Contractor>> getAllContractorsByJobId(@PathVariable(value = "laborId") Long laborId) {
        return contractorService.getAllContractorsByJobId(laborId);
    }

    @PostMapping("/labor/{laborId}/contractors")
    public ResponseEntity<Contractor> createContractor(@PathVariable(value = "laborId") Long laborId,
                                                       @RequestBody Contractor newContractor) {
        return contractorService.createContractor(laborId, newContractor);
    }


    @PutMapping("/contractor/{id}")
    public ResponseEntity<Contractor> updateContractor(@PathVariable("id") long id, @RequestBody Contractor updatedContractor) {
        return contractorService.updateContractor(id, updatedContractor);
    }

    @DeleteMapping("/contractor/{id}")
    public ResponseEntity<HttpStatus> deleteContractor(@PathVariable("id") long id) {
        return contractorService.deleteContractor(id);
    }

    @DeleteMapping("/labor/{laborId}/contractors")
    public ResponseEntity<List<Order>> deleteAllContractorsOfLabor(@PathVariable(value = "laborId") Long laborId) {
        return contractorService.deleteAllContractorsOfLabor(laborId);
    }





}
