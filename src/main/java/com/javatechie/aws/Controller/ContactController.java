package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.DAO.ContactRepository;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Contact with id = " + id));

        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") long id, @RequestBody Contact updatedContact) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactId " + id + "not found"));

        contact.setCompany(updatedContact.getCompany());
        contact.setCustomer(updatedContact.getCustomer());
        contact.setPhone(updatedContact.getPhone());
        contact.setEmail(updatedContact.getEmail());
        contact.setJobTitle(updatedContact.getJobTitle());
        contact.setName(updatedContact.getName());

        return new ResponseEntity<>(contactRepository.save(contact), HttpStatus.OK);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") long id) {
        contactRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/company/{companyId}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsByCompanyId(@PathVariable(value = "companyId") Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Not found Company with id = " + companyId);
        }
        List<Contact> contacts = contactRepository.findByCompanyId(companyId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }


    @PostMapping("/company/{companyId}/contacts")
    public ResponseEntity<Contact> createContact(@PathVariable(value = "companyId") Long companyId,
                                                 @RequestBody Contact newContact) {
        Contact contact = companyRepository.findById(companyId).map(company -> {
            newContact.setCompany(company);
            return contactRepository.save(newContact);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Company with id = " + companyId));

        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }


    @DeleteMapping("/company/{companyId}/contacts")
    public ResponseEntity<List<Contact>> deleteAllContactsOfCompany(@PathVariable(value = "companyId") Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Not found Company with id = " + companyId);
        }

        contactRepository.deleteByCompanyId(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/customer/{customerId}/contacts")
    public ResponseEntity<Contact> addContact(@PathVariable(value = "customerId") Long customerId,
                                              @RequestBody Contact newContact) {
        Contact contact = contactRepository.findById(newContact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ContactId " + newContact.getId() + "not found"));


        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId " + customerId + "not found"));
        contact.setCustomer(customer);


        return new ResponseEntity<>(contactRepository.save(contact), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Not found Customer with id = " + customerId);
        }
        List<Contact> contacts = contactRepository.findByCustomerId(customerId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @DeleteMapping("/customer/{customerId}/contacts")
    public ResponseEntity<List<Contact>> deleteAllContactsOfCustomer(@PathVariable(value = "customerId") Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Not found Customer with id = " + customerId);
        }

        contactRepository.deleteByCustomerId(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
