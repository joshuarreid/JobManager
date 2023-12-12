package com.javatechie.aws.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
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


    public ResponseEntity<List<Company>> getAllCompanies() {
        try {
            List<Company> companies = new ArrayList<>();
            companyRepository.findAll().forEach(companies::add);
            logger.info(companies.toString());
            if (companies.isEmpty()) {
                logger.info("No Companies Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Companies Successfully Fetched");
            return new ResponseEntity<>(companies, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<Company> getCompanyById(long id) {
        try {
            Company company = companyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Company does not exist: id=" + id));
            logger.info(company.toString());
            logger.info("Company Successfully Fetched");
            return new ResponseEntity<>(company, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<Company> createCompany(Company newCompany) {
        try {
            if (newCompany.getName() == null) {
                logger.info("name is null");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Company company = companyRepository.save(newCompany);
            logger.info(company);
            logger.info("Company Successfully Created");
            return new ResponseEntity<>(company, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
            logger.info("Company Successfully Updated");
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