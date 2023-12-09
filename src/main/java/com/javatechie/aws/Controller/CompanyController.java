package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.Model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies(@RequestParam(required = false) String name) {
        List<Company> companies = new ArrayList<Company>();

        companyRepository.findAll().forEach(companies::add);


        if (companies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Company with id = " + id));

        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestBody Company newCompany) {
        Company company = companyRepository.save(newCompany);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") long id, @RequestBody Company updatedCompany) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Company with id = " + id));
        company.setAddress(updatedCompany.getAddress());
        company.setName(updatedCompany.getName());
        return new ResponseEntity<>(companyRepository.save(company), HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable("id") long id) {
        companyRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/company")
    public ResponseEntity<HttpStatus> deleteAllCompanies() {
        companyRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
