package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.Model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService
{
    @Autowired
    CompanyRepository companyRepository;


    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = new ArrayList<Company>();

        companyRepository.findAll().forEach(companies::add);


        if (companies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(companies, HttpStatus.OK);
    }


    public ResponseEntity<Company> getCompanyById(long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Company with id = " + id));

        return new ResponseEntity<>(company, HttpStatus.OK);
    }


    public ResponseEntity<Company> createCompany(Company newCompany) {
        Company company = companyRepository.save(newCompany);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }


    public ResponseEntity<Company> updateCompany(long id, Company updatedCompany) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Company with id = " + id));
        company.setAddress(updatedCompany.getAddress());
        company.setName(updatedCompany.getName());
        return new ResponseEntity<>(companyRepository.save(company), HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> deleteCompany(long id) {
        companyRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<HttpStatus> deleteAllCompanies() {
        companyRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}