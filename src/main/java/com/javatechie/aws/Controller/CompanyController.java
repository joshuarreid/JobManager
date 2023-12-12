package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Service.CompanyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    private static final Logger logger = LogManager.getLogger(CompanyController.class);


    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies() {
        logger.info("Fetching All Companies...");
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") long id) {
        logger.info("Fetching CompanyId: " + id);
        return companyService.getCompanyById(id);
    }

    @PostMapping("/company")
    @ResponseBody
    public ResponseEntity<Company> createCompany(@RequestBody Company newCompany) {
        logger.info("Creating Company...");
        logger.info(newCompany.toString());
        return companyService.createCompany(newCompany);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") long id, @RequestBody Company updatedCompany) {
        logger.info("Updating CompanyId: " + id);
        return companyService.updateCompany(id, updatedCompany);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable("id") long id) {
        logger.info("Deleting CompanyId: " + id);
        return companyService.deleteCompany(id);
    }



}
