package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.DAO.ContactRepository;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;

    private static final Logger logger = LogManager.getLogger(ContactService.class);


    public ResponseEntity<Object> getAllContacts() {
        Iterable<Contact> contacts = new ArrayList<>();
        contacts = contactRepository.findAll();
        logger.info(contacts);
        return ResponseHandler.generateResponse(contacts);
    }


    public ResponseEntity<Object> getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact does not exist: id=" + id));
        logger.info(contact);
        return ResponseHandler.generateResponse(contact);
    }


    public ResponseEntity<Object> updateContact(long id, Contact updatedContact) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact does not exist: id=" + id));
        contact.setCompany(updatedContact.getCompany());
        contact.setCustomer(updatedContact.getCustomer());
        contact.setPhone(updatedContact.getPhone());
        contact.setEmail(updatedContact.getEmail());
        contact.setJobTitle(updatedContact.getJobTitle());
        contact.setName(updatedContact.getName());
        contact.setImage(updatedContact.getImage());
        contact = contactRepository.save(contact);
        logger.info(contact);
        return ResponseHandler.generateResponse(contact);
    }


    public ResponseEntity<Object> deleteContact(long id) {
        contactRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


    public ResponseEntity<Object> getAllContactsByCompanyId(Long companyId) {
        List<Contact> contacts = new ArrayList<>();
        contacts = contactRepository.findByCompanyId(companyId);
        logger.info(contacts);
        return ResponseHandler.generateResponse(contacts);
    }


    public ResponseEntity<Object> createContact(Long companyId, Contact newContact) {
        Contact contact = companyRepository.findById(companyId).map(company -> {
            newContact.setCompany(company);
            return contactRepository.save(newContact);
        }).orElseThrow(() -> new ResourceNotFoundException("Company does not exist: id=" + companyId));
        logger.info(contact);
        return ResponseHandler.generateResponse(contact);
    }


    public ResponseEntity<Object> deleteAllContactsOfCompany(Long companyId) {
        contactRepository.deleteByCompanyId(companyId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


    public ResponseEntity<Object> addContactToCustomer(Long customerId, Contact newContact) {
        Contact contact = contactRepository.findById(newContact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact does not exist: id=" + newContact.getId()));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + customerId));
        contact.setCustomer(customer);
        contactRepository.save(contact);
        logger.info(contact);
        return ResponseHandler.generateResponse(contact);
    }


    public ResponseEntity<Object> getAllContactsByCustomerId(Long customerId) {
        List<Contact> contacts = new ArrayList<>();
        contacts = contactRepository.findByCustomerId(customerId);
        return ResponseHandler.generateResponse(contacts);
    }

}