package com.javatechie.aws.Controller;


import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Service.ContactService;
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
    ContactService contactService;



    @GetMapping("/contact")
    public ResponseEntity<Iterable<Contact>> getContacts() {
        return contactService.getContacts();
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long id) {
        return contactService.getContactById(id);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") long id, @RequestBody Contact updatedContact) {
        return contactService.updateContact(id, updatedContact);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") long id) {
        return contactService.deleteContact(id);
    }

    @GetMapping("/company/{companyId}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsByCompanyId(@PathVariable(value = "companyId") Long companyId) {
        return contactService.getAllContactsByCompanyId(companyId);
    }


    @PostMapping("/company/{companyId}/contacts")
    public ResponseEntity<Contact> createContact(@PathVariable(value = "companyId") Long companyId,
                                                 @RequestBody Contact newContact) {
        return contactService.createContact(companyId, newContact);
    }


    @DeleteMapping("/company/{companyId}/contacts")
    public ResponseEntity<List<Contact>> deleteAllContactsOfCompany(@PathVariable(value = "companyId") Long companyId) {
        return contactService.deleteAllContactsOfCompany(companyId);
    }


    @PutMapping("/customer/{customerId}/contacts")
    public ResponseEntity<Contact> addContactToCustomer(@PathVariable(value = "customerId") Long customerId,
                                              @RequestBody Contact newContact) {
        return contactService.addContactToCustomer(customerId, newContact);
    }

    @GetMapping("/customer/{customerId}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return contactService.getAllContactsByCustomerId(customerId);
    }

}
