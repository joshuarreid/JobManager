package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Service.CompanyService;
import com.javatechie.aws.common.utility.ResponseHandler;
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
public class CompanyController {
    @Autowired
    CompanyService companyService;

    private static final Logger logger = LogManager.getLogger(CompanyController.class);


    @GetMapping("/company")
    public ResponseEntity<Object> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Object> getCompanyById(@PathVariable("id") long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping("/company")
    @ResponseBody
    public ResponseEntity<Object> createCompany(@RequestBody Company newCompany) {
        return companyService.createCompany(newCompany);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<Object> updateCompany(@PathVariable("id") long id, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(id, updatedCompany);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable("id") long id) {
        return companyService.deleteCompany(id);
    }



}
