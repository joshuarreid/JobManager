package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.DAO.ContactRepository;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ContactService.class);



    public ResponseEntity<Iterable<Contact>> getAllContacts() {
        try {
            Iterable<Contact> contacts = new ArrayList<>();
            contacts = contactRepository.findAll();
            logger.info(contacts.toString());
            if (!contacts.iterator().hasNext()) {
                logger.info("No Contacts Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Contacts Successfully Fetched");
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<Contact> getContactById(Long id) {
        try {
            Contact contact = contactRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Contact does not exist: id=" + id));
            logger.info(contact.toString());
            logger.info("Contact Successfully Fetched");
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Contact> updateContact(long id, Contact updatedContact) {
        try {
            if (!contactRepository.existsById(id)) {
                throw new ResourceNotFoundException("Contact does not exist: id=" + id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
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
            logger.info("Contact Successfully Updated");
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<HttpStatus> deleteContact(long id) {
        try {
            if (!contactRepository.existsById(id)) {
                throw new ResourceNotFoundException("Contact does not exist: id=" + id);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
            contactRepository.deleteById(id);
            logger.info("Contact Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Contact>> getAllContactsByCompanyId(Long companyId) {
        try {
            if (!companyRepository.existsById(companyId)) {
                throw new ResourceNotFoundException("Company does not exist: id=" + companyId);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


        try {
            List<Contact> contacts = new ArrayList<>();
            contacts = contactRepository.findByCompanyId(companyId);
            if (contacts.isEmpty()) {
                logger.info("No Contacts Found for CompanyId: " + companyId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(contacts.size() + " Contacts Successfully Found for CompanyId: " + companyId);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Contact> createContact(Long companyId, Contact newContact) {
        try {
            Contact contact = companyRepository.findById(companyId).map(company -> {
                newContact.setCompany(company);
                return contactRepository.save(newContact);
            }).orElseThrow(() -> new ResourceNotFoundException("Company does not exist: id=" + companyId));
            logger.info(contact);
            logger.info("Contact Successfully Created for CompanyId: " + companyId);
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }



    public ResponseEntity<HttpStatus> deleteAllContactsOfCompany(Long companyId) {
        try {
            if (!companyRepository.existsById(companyId)) {
                throw new ResourceNotFoundException("Company does not exist: id=" + companyId);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        try {
            contactRepository.deleteByCompanyId(companyId);
            logger.info("All Contacts Successfully Deleted for CompanyId: " + companyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Contact> addContactToCustomer(Long customerId, Contact newContact) {
        try {
            if (!contactRepository.existsById(newContact.getId())) {
                throw new ResourceNotFoundException("Contact does not exist: id=" + newContact.getId());
            }

            if (!customerRepository.existsById(customerId)) {
                throw new ResourceNotFoundException("Customer does not exist: id=" + customerId);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        try {
            Contact contact = contactRepository.findById(newContact.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contact does not exist: id=" + newContact.getId()));
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist: id=" + customerId));
            contact.setCustomer(customer);
            contactRepository.save(contact);
            logger.info(contact);
            logger.info("Contact Successfully Added for CustomerId: " + customerId);
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Contact>> getAllContactsByCustomerId(Long customerId) {
        try {
            if (!customerRepository.existsById(customerId)) {
                throw new ResourceNotFoundException("Customer does not exist: id=" + customerId);
            }
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        try {

            List<Contact> contacts = new ArrayList<>();
            contacts = contactRepository.findByCustomerId(customerId);
            if (contacts.isEmpty()) {
                logger.info("No Contacts Found for CustomerId: " + customerId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(contacts.size() + " Contacts Successfully Found for CustomerId: " + customerId);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}