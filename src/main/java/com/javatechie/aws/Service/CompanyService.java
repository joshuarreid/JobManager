package com.javatechie.aws.Service;

import com.javatechie.aws.common.exception.ValidationException;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.Model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService
{
    @Autowired
    CompanyRepository companyRepository;

    private static final Logger logger = LogManager.getLogger(CompanyService.class);


    public ResponseEntity<Object> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companies::add);
        if (companies.isEmpty()) {
            return ResponseHandler.generateErrorResponse(companies, HttpStatus.BAD_REQUEST);
        }
        logger.info(companies.toString());
        return ResponseHandler.generateResponse(companies);
    }


    public ResponseEntity<Object> getCompanyById(long id) {
            Company company = companyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("company id does not exist: " + id));
            logger.info(company.toString());
            return ResponseHandler.generateResponse(company);
    }


    public ResponseEntity<Object> createCompany(Company newCompany) {
        Company company = companyRepository.save(newCompany);
        logger.info(company.toString());
        return ResponseHandler.generateResponse(company);

    }


    public ResponseEntity<Company> updateCompany(long id, Company updatedCompany) {
        try {
            if (!companyRepository.existsById(id)) {
                throw new ResourceNotFoundException("Company does not exist: id=" + id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
            Company company = companyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Company does not exist: id=" + id));
            company.setAddress(updatedCompany.getAddress());
            company.setName(updatedCompany.getName());
            company.setImage(updatedCompany.getImage());
            company = companyRepository.save(company);
            logger.info(company);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<HttpStatus> deleteCompany(long id) {
        try {
            if (!companyRepository.existsById(id)) {
                throw new ResourceNotFoundException("Company does not exist: id=" + id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
            companyRepository.deleteById(id);
            logger.info("Company Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}