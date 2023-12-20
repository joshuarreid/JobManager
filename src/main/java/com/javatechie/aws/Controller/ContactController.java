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
@CrossOrigin
@RequestMapping("/api")
public class ContactController {
    @Autowired
    ContactService contactService;

    private static final Logger logger = LogManager.getLogger(ContactController.class);


    @GetMapping("/contact")
    public ResponseEntity<Object> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Object> getContactById(@PathVariable(value = "id") Long id) {
        return contactService.getContactById(id);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Object> updateContact(@PathVariable("id") long id, @RequestBody Contact updatedContact) {
        return contactService.updateContact(id, updatedContact);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable("id") long id) {
        return contactService.deleteContact(id);
    }

    @GetMapping("/company/{companyId}/contacts")
    public ResponseEntity<Object> getAllContactsByCompanyId(@PathVariable(value = "companyId") Long companyId) {
        return contactService.getAllContactsByCompanyId(companyId);
    }


    @PostMapping("/company/{companyId}/contacts")
    public ResponseEntity<Object> createContact(@PathVariable(value = "companyId") Long companyId,
                                                 @RequestBody Contact newContact) {
        return contactService.createContact(companyId, newContact);
    }


    @DeleteMapping("/company/{companyId}/contacts")
    public ResponseEntity<Object> deleteAllContactsOfCompany(@PathVariable(value = "companyId") Long companyId) {
        return contactService.deleteAllContactsOfCompany(companyId);
    }


    @PutMapping("/customer/{customerId}/contacts")
    public ResponseEntity<Object> addContactToCustomer(@PathVariable(value = "customerId") Long customerId,
                                              @RequestBody Contact newContact) {
        return contactService.addContactToCustomer(customerId, newContact);
    }

    @GetMapping("/customer/{customerId}/contacts")
    public ResponseEntity<Object> getAllContactsByCustomerId(@PathVariable(value = "customerId") Long customerId) {
        return contactService.getAllContactsByCustomerId(customerId);
    }

}
