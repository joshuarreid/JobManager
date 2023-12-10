package com.javatechie.aws.Service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService
{
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CustomerRepository customerRepository;



    public ResponseEntity<Iterable<Contact>> getContacts() {
        return new ResponseEntity<>(contactRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<Contact> getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Contact with id = " + id));

        return new ResponseEntity<>(contact, HttpStatus.OK);
    }


    public ResponseEntity<Contact> updateContact(long id, Contact updatedContact) {
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


    public ResponseEntity<HttpStatus> deleteContact(long id) {
        contactRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<List<Contact>> getAllContactsByCompanyId(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Not found Company with id = " + companyId);
        }
        List<Contact> contacts = contactRepository.findByCompanyId(companyId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }



    public ResponseEntity<Contact> createContact(Long companyId, Contact newContact) {
        Contact contact = companyRepository.findById(companyId).map(company -> {
            newContact.setCompany(company);
            return contactRepository.save(newContact);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Company with id = " + companyId));

        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }



    public ResponseEntity<List<Contact>> deleteAllContactsOfCompany(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Not found Company with id = " + companyId);
        }

        contactRepository.deleteByCompanyId(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    public ResponseEntity<Contact> addContactToCustomer(Long customerId, Contact newContact) {
        Contact contact = contactRepository.findById(newContact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ContactId " + newContact.getId() + "not found"));


        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerId " + customerId + "not found"));
        contact.setCustomer(customer);


        return new ResponseEntity<>(contactRepository.save(contact), HttpStatus.OK);
    }


    public ResponseEntity<List<Contact>> getAllContactsByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Not found Customer with id = " + customerId);
        }
        List<Contact> contacts = contactRepository.findByCustomerId(customerId);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

//    @DeleteMapping("/customer/{customerId}/contacts/{contactId}")
//    public ResponseEntity<List<Contact>> deleteAllContactsOfCustomer(@PathVariable(value = "customerId") Long customerId,
//                                                                     @PathVariable(value = "contactId") Long contactId) {
//
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("contactId " + customerId + "not found"));
//
//        Contact contact = contactRepository.findById(contactId)
//                .orElseThrow(() -> new ResourceNotFoundException("contactId " + customerId + "not found"));
//
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}