package com.javatechie.aws.Service;


import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.Controller.CompanyController;
import com.javatechie.aws.DAO.CompanyRepository;
import com.javatechie.aws.DAO.ContactRepository;
import com.javatechie.aws.DAO.CustomerRepository;
import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Model.Contact;
import com.javatechie.aws.Model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class ContactServiceTests {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CustomerRepository customerRepository;


    private Contact mockContact;

    private Contact mockContact2;

    private Contact mockContactWithCustomer;

    private Customer mockCustomer;

    private Company mockCompany;


    @Before
    public void createMockContacts() {

        Company mockCompany = new Company();
        mockCompany.setName("Mock Company");
        mockCompany.setAddress("1234 Mock Avenue");
        mockCompany.setId(1L);
        this.mockCompany = mockCompany;


        Contact mockContact = new Contact();
        mockContact.setName("John Mocker");
        mockContact.setJobTitle("Head Mock Advisor");
        mockContact.setCompany(this.mockCompany);
        mockContact.setEmail("mock@mock.com");
        mockContact.setPhone(1234567890);
        mockContact.setId(1L);
        this.mockContact = mockContact;

        Contact mockContact2 = new Contact();
        mockContact2.setName("John Mocker");
        mockContact2.setJobTitle("FIRED");
        mockContact2.setCompany(this.mockCompany);
        mockContact2.setEmail("mock@mock.com");
        mockContact2.setPhone(1234567890);
        mockContact2.setId(1L);
        this.mockContact2 = mockContact2;

        List<String> addresses = new ArrayList<>();
        addresses.add("4565 Rd");
        addresses.add("1234 Lane");
        Customer mockCustomer = new Customer();
        mockCustomer.setName("Mock Customer 1");
        mockCustomer.setAddresses(addresses);
        mockCustomer.setId(1L);
        this.mockCustomer = mockCustomer;

        mockContact.setCustomer(mockCustomer);
        this.mockContactWithCustomer = mockContact;
    }

    @Test
    public void testGetAllContacts(){
        List<Contact> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContact);
        ResponseEntity<Iterable<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(contactRepository.findAll()).thenReturn((Iterable<Contact>) mockResponseList);


        ResponseEntity<Iterable<Contact>> result = contactService.getAllContacts();
        List<Contact> body = (List) result.getBody();
        Assert.assertEquals(body.get(0).getId(), this.mockContact.getId());
        Assert.assertEquals(body.get(0).getCompany(), this.mockContact.getCompany());
        Assert.assertEquals(body.get(0).getName(), this.mockContact.getName());
        Assert.assertEquals(body.get(0).getJobTitle(), this.mockContact.getJobTitle());
        Assert.assertEquals(body.get(0).getEmail(), this.mockContact.getEmail());
        Assert.assertEquals(body.get(0).getPhone(), this.mockContact.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testGetAllContactsByCompanyId(){
        List<Contact> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContact);
        ResponseEntity<Iterable<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(companyRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.findByCompanyId(1L)).thenReturn((List<Contact>) mockResponseList);

        ResponseEntity<List<Contact>> result = contactService.getAllContactsByCompanyId(1L);
        List<Contact> body = (List) result.getBody();
        Assert.assertEquals(body.get(0).getId(), this.mockContact.getId());
        Assert.assertEquals(body.get(0).getCompany(), this.mockContact.getCompany());
        Assert.assertEquals(body.get(0).getName(), this.mockContact.getName());
        Assert.assertEquals(body.get(0).getJobTitle(), this.mockContact.getJobTitle());
        Assert.assertEquals(body.get(0).getEmail(), this.mockContact.getEmail());
        Assert.assertEquals(body.get(0).getPhone(), this.mockContact.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetAllContactsByCompanyIdInvalidId(){
        List<Contact> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContact);
        ResponseEntity<Iterable<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(companyRepository.existsById(1L)).thenReturn(false);
        when(contactRepository.findByCompanyId(1L)).thenReturn((List<Contact>) mockResponseList);

        ResponseEntity<List<Contact>> result = contactService.getAllContactsByCompanyId(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetAllContactsByCustomerId(){
        List<Contact> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContact);
        ResponseEntity<Iterable<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.findByCustomerId(1L)).thenReturn((List<Contact>) mockResponseList);

        ResponseEntity<List<Contact>> result = contactService.getAllContactsByCustomerId(1L);
        List<Contact> body = (List) result.getBody();
        Assert.assertEquals(body.get(0).getId(), this.mockContact.getId());
        Assert.assertEquals(body.get(0).getCompany(), this.mockContact.getCompany());
        Assert.assertEquals(body.get(0).getName(), this.mockContact.getName());
        Assert.assertEquals(body.get(0).getJobTitle(), this.mockContact.getJobTitle());
        Assert.assertEquals(body.get(0).getEmail(), this.mockContact.getEmail());
        Assert.assertEquals(body.get(0).getPhone(), this.mockContact.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testGetAllContactsByCustomerIdInvalidId(){
        List<Contact> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContact);
        ResponseEntity<Iterable<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(customerRepository.existsById(1L)).thenReturn(false);
        when(contactRepository.findByCustomerId(1L)).thenReturn((List<Contact>) mockResponseList);

        ResponseEntity<List<Contact>> result = contactService.getAllContactsByCompanyId(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetAllContactsEmpty(){
        List<Contact> mockResponseList = new ArrayList<>();
        when(contactRepository.findAll()).thenReturn((Iterable<Contact>) mockResponseList);
        ResponseEntity<Iterable<Contact>> result = contactService.getAllContacts();
        Assert.assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);

    }

    @Test
    public void testGetContactById(){
        ResponseEntity<Contact> mockResponse = new ResponseEntity<>(this.mockContact, HttpStatus.OK);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContact));


        ResponseEntity<Contact> result = contactService.getContactById(1L);
        Assert.assertEquals(result.getBody().getId(), this.mockContact.getId());
        Assert.assertEquals(result.getBody().getCompany(), this.mockContact.getCompany());
        Assert.assertEquals(result.getBody().getName(), this.mockContact.getName());
        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContact.getJobTitle());
        Assert.assertEquals(result.getBody().getEmail(), this.mockContact.getEmail());
        Assert.assertEquals(result.getBody().getPhone(), this.mockContact.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testGetContactByIdInvalidId(){
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseEntity<Contact> result = contactService.getContactById(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateContact(){
        Contact mockContactNoId = new Contact();
        mockContactNoId.setName("John Mocker");
        mockContactNoId.setJobTitle("Head Mock Advisor");
        mockContactNoId.setEmail("mock@mock.com");
        mockContactNoId.setPhone(1234567890);


        when(contactRepository.save(mockContactNoId)).thenReturn(this.mockContact);
        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCompany));
        ResponseEntity<Contact> result = contactService.createContact(1L, mockContactNoId);
        Assert.assertEquals(result.getBody().getId(), this.mockContact.getId());
        Assert.assertEquals(result.getBody().getCompany(), this.mockContact.getCompany());
        Assert.assertEquals(result.getBody().getName(), this.mockContact.getName());
        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContact.getJobTitle());
        Assert.assertEquals(result.getBody().getEmail(), this.mockContact.getEmail());
        Assert.assertEquals(result.getBody().getPhone(), this.mockContact.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAddContactToCustomer() {

        when(contactRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContact));
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCustomer));
        when(contactRepository.save(this.mockContactWithCustomer)).thenReturn(this.mockContactWithCustomer);

        ResponseEntity<Contact> result = contactService.addContactToCustomer(1L, this.mockContact);
        Assert.assertEquals(result.getBody().getId(), this.mockContactWithCustomer.getId());
        Assert.assertEquals(result.getBody().getCompany(), this.mockContactWithCustomer.getCompany());
        Assert.assertEquals(result.getBody().getName(), this.mockContactWithCustomer.getName());
        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContactWithCustomer.getJobTitle());
        Assert.assertEquals(result.getBody().getEmail(), this.mockContactWithCustomer.getEmail());
        Assert.assertEquals(result.getBody().getPhone(), this.mockContactWithCustomer.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAddContactToCustomerInvalidContactId() {

        when(contactRepository.existsById(1L)).thenReturn(false);
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContact));
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCustomer));
        when(contactRepository.save(this.mockContactWithCustomer)).thenReturn(this.mockContactWithCustomer);

        ResponseEntity<Contact> result = contactService.addContactToCustomer(1L, this.mockContact);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddContactToCustomerInvalidCustomerId() {

        when(contactRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.existsById(1L)).thenReturn(false);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContact));
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCustomer));
        when(contactRepository.save(this.mockContactWithCustomer)).thenReturn(this.mockContactWithCustomer);

        ResponseEntity<Contact> result = contactService.addContactToCustomer(1L, this.mockContact);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateContactInvalidCompanyId(){
        Contact mockContactNoId = new Contact();
        mockContactNoId.setName("John Mocker");
        mockContactNoId.setJobTitle("Head Mock Advisor");
        mockContactNoId.setEmail("mock@mock.com");
        mockContactNoId.setPhone(1234567890);


        when(contactRepository.save(mockContactNoId)).thenReturn(this.mockContact);
        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCompany));
        ResponseEntity<Contact> result = contactService.createContact(2L, mockContactNoId);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateContact(){
        when(contactRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContact));
        when(contactRepository.save(mockContact2)).thenReturn(this.mockContact2);


        ResponseEntity<Contact> result = contactService.updateContact(1L, mockContact2);
        Assert.assertEquals(result.getBody().getId(), this.mockContact2.getId());
        Assert.assertEquals(result.getBody().getCompany(), this.mockContact2.getCompany());
        Assert.assertEquals(result.getBody().getName(), this.mockContact2.getName());
        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContact2.getJobTitle());
        Assert.assertEquals(result.getBody().getEmail(), this.mockContact2.getEmail());
        Assert.assertEquals(result.getBody().getPhone(), this.mockContact2.getPhone());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testUpdateContactInvalidId(){
        when(contactRepository.existsById(1L)).thenReturn(false);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContact));
        when(contactRepository.save(mockContact2)).thenReturn(this.mockContact2);


        ResponseEntity<Contact> result = contactService.updateContact(2L, mockContact2);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testDeleteContact(){
        when(contactRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<HttpStatus> result = contactService.deleteContact(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(contactRepository, times(1)).deleteById(1L);

    }

    @Test
    public void testDeleteContactInvalidId(){
        when(contactRepository.existsById(1L)).thenReturn(false);
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseEntity<HttpStatus> result = contactService.deleteContact(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testDeleteAllContactsOfCompany(){
        when(companyRepository.existsById(1L)).thenReturn(true);


        ResponseEntity<HttpStatus> result = contactService.deleteAllContactsOfCompany(1L);
        verify(contactRepository, times(1)).deleteByCompanyId(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

}
