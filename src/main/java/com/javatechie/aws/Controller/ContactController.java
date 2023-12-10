package com.javatechie.aws.Controller;


import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Service.ContactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    ContactService contactService;

    private static final Logger logger = LogManager.getLogger(ContactController.class);


    @GetMapping("/contact")
    public ResponseEntity<Iterable<Contact>> getAllContacts() {
        logger.info("Fetching All Contacts...");
        return contactService.getAllContacts();
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long id) {
        logger.info("Fetching ContactId: " + id);
        return contactService.getContactById(id);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") long id, @RequestBody Contact updatedContact) {
        logger.info("Updating CompanyId: " + id);
        return contactService.updateContact(id, updatedContact);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") long id) {
        logger.info("Deleting CompanyId: " + id);
        return contactService.deleteContact(id);
    }

    @GetMapping("/company/{companyId}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsByCompanyId(@PathVariable(value = "companyId") Long companyId) {
        logger.info("Fetching Contacts for CompanyId: " + companyId);
        return contactService.getAllContactsByCompanyId(companyId);
    }


    @PostMapping("/company/{companyId}/contacts")
    public ResponseEntity<Contact> createContact(@PathVariable(value = "companyId") Long companyId,
                                                 @RequestBody Contact newContact) {
        logger.info("Creating Contact...");
        logger.info(newContact.toString());
        return contactService.createContact(companyId, newContact);
    }


    @DeleteMapping("/company/{companyId}/contacts")
    public ResponseEntity<List<Contact>> deleteAllContactsOfCompany(@PathVariable(value = "companyId") Long companyId) {
        logger.info("Deleting All Contacts for CompanyId: " + companyId);
        return contactService.deleteAllContactsOfCompany(companyId);
    }


    @PutMapping("/customer/{customerId}/contacts")
    public ResponseEntity<Contact> addContactToCustomer(@PathVariable(value = "customerId") Long customerId,
                                              @RequestBody Contact newContact) {
        logger.info("Adding Contact to CustomerId: " + customerId);
        logger.info(newContact.toString());
        return contactService.addContactToCustomer(customerId, newContact);
    }

    @GetMapping("/customer/{customerId}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        logger.info("Fetching All Contacts for CustomerId: " + customerId);
        return contactService.getAllContactsByCustomerId(customerId);
    }

}
