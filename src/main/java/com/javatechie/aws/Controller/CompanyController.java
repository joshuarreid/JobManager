package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Service.CompanyService;
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

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestBody Company newCompany) {
        return companyService.createCompany(newCompany);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") long id, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(id, updatedCompany);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable("id") long id) {
        return companyService.deleteCompany(id);
    }

    @DeleteMapping("/company")
    public ResponseEntity<HttpStatus> deleteAllCompanies() {
        return companyService.deleteAllCompanies();
    }

}
