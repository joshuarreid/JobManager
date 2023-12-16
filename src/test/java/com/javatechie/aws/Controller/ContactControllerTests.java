//package com.javatechie.aws.Controller;
//
//import com.javatechie.aws.Model.Company;
//import com.javatechie.aws.Model.Contact;
//import com.javatechie.aws.Model.Customer;
//import com.javatechie.aws.Service.ContactService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//public class ContactControllerTests {
//    @InjectMocks
//    private ContactController contactController;
//
//    @Mock
//    private ContactService contactService;
//
//    private Contact mockContact;
//
//    private Contact mockContactWithCustomer;
//
//    private Customer mockCustomer;
//
//    private Company mockCompany;
//
//    @Before
//    public void createMockContacts() {
//
//        Company mockCompany = new Company();
//        mockCompany.setName("Mock Company");
//        mockCompany.setAddress("1234 Mock Avenue");
//        mockCompany.setImage("http://photo.com");
//        mockCompany.setId(1L);
//        this.mockCompany = mockCompany;
//
//
//        Contact mockContact = new Contact();
//        mockContact.setName("John Mocker");
//        mockContact.setJobTitle("Head Mock Advisor");
//        mockContact.setCompany(this.mockCompany);
//        mockContact.setImage("http://photo.com");
//        mockContact.setId(1L);
//        this.mockContact = mockContact;
//
//        List<String> addresses = new ArrayList<>();
//        addresses.add("4565 Rd");
//        addresses.add("1234 Lane");
//        Customer mockCustomer = new Customer();
//        mockCustomer.setName("Mock Customer 1");
//        mockCustomer.setAddresses(addresses);
//        mockCustomer.setImage("http://photo.com");
//        mockCustomer.setId(1L);
//        this.mockCustomer = mockCustomer;
//
//        mockContact.setCustomer(mockCustomer);
//        this.mockContactWithCustomer = mockContact;
//    }
//
//
//    @Test
//    public void testGetAllContacts(){
//        List<Contact> mockResponseList = new ArrayList<>();
//        mockResponseList.add(this.mockContact);
//        ResponseEntity<Iterable<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
//        when(contactService.getAllContacts()).thenReturn(mockResponse);
//
//
//        ResponseEntity<Iterable<Contact>> result = contactController.getAllContacts();
//        List<Contact> body = (List) result.getBody();
//        Assert.assertEquals(body.get(0).getId(), this.mockContact.getId());
//        Assert.assertEquals(body.get(0).getCompany(), this.mockContact.getCompany());
//        Assert.assertEquals(body.get(0).getName(), this.mockContact.getName());
//        Assert.assertEquals(body.get(0).getJobTitle(), this.mockContact.getJobTitle());
//        Assert.assertEquals(body.get(0).getImage(), this.mockContact.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testGetAllContactsOfCompany(){
//        List<Contact> mockResponseList = new ArrayList<>();
//        mockResponseList.add(this.mockContact);
//        ResponseEntity<List<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
//        when(contactService.getAllContactsByCompanyId(1L)).thenReturn(mockResponse);
//
//
//        ResponseEntity<List<Contact>> result = contactController.getAllContactsByCompanyId(1L);
//        List<Contact> body = (List) result.getBody();
//        Assert.assertEquals(body.get(0).getId(), this.mockContact.getId());
//        Assert.assertEquals(body.get(0).getCompany(), this.mockContact.getCompany());
//        Assert.assertEquals(body.get(0).getName(), this.mockContact.getName());
//        Assert.assertEquals(body.get(0).getJobTitle(), this.mockContact.getJobTitle());
//        Assert.assertEquals(body.get(0).getImage(), this.mockContact.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testGetAllContactsOfCustomer(){
//        List<Contact> mockResponseList = new ArrayList<>();
//        mockResponseList.add(this.mockContact);
//        ResponseEntity<List<Contact>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
//        when(contactService.getAllContactsByCustomerId(1L)).thenReturn(mockResponse);
//
//
//        ResponseEntity<List<Contact>> result = contactController.getAllContactsByCustomerId(1L);
//        List<Contact> body = (List) result.getBody();
//        Assert.assertEquals(body.get(0).getId(), this.mockContact.getId());
//        Assert.assertEquals(body.get(0).getCompany(), this.mockContact.getCompany());
//        Assert.assertEquals(body.get(0).getName(), this.mockContact.getName());
//        Assert.assertEquals(body.get(0).getJobTitle(), this.mockContact.getJobTitle());
//        Assert.assertEquals(body.get(0).getImage(), this.mockContact.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//
//
//    @Test
//    public void testGetContactById(){
//        ResponseEntity<Contact> mockResponse = new ResponseEntity<>(this.mockContact, HttpStatus.OK);
//        when(contactService.getContactById(1L)).thenReturn(mockResponse);
//
//
//        ResponseEntity<Contact> result = contactController.getContactById(1L);
//        Assert.assertEquals(result.getBody().getId(), this.mockContact.getId());
//        Assert.assertEquals(result.getBody().getCompany(), this.mockContact.getCompany());
//        Assert.assertEquals(result.getBody().getName(), this.mockContact.getName());
//        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContact.getJobTitle());
//        Assert.assertEquals(result.getBody().getImage(), this.mockContact.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testCreateContact(){
//        Contact mockContactNoId = new Contact();
//        mockContactNoId.setName("John Mocker");
//        mockContactNoId.setJobTitle("Head Mock Advisor");
//        mockContactNoId.setCompany(this.mockCompany);
//
//        ResponseEntity<Contact> mockResponse = new ResponseEntity<>(this.mockContact, HttpStatus.OK);
//        when(contactService.createContact(1L, mockContactNoId)).thenReturn(mockResponse);
//
//
//        ResponseEntity<Contact> result = contactController.createContact(1L, mockContactNoId);
//        Assert.assertEquals(result.getBody().getId(), this.mockContact.getId());
//        Assert.assertEquals(result.getBody().getCompany(), this.mockContact.getCompany());
//        Assert.assertEquals(result.getBody().getName(), this.mockContact.getName());
//        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContact.getJobTitle());
//        Assert.assertEquals(result.getBody().getImage(), this.mockContact.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//    @Test
//    public void testAddContactToCustomer(){
//        ResponseEntity<Contact> mockResponse = new ResponseEntity<>(this.mockContactWithCustomer, HttpStatus.OK);
//        when(contactService.addContactToCustomer(1L, this.mockContact)).thenReturn(mockResponse);
//
//        ResponseEntity<Contact> result = contactController.addContactToCustomer(1L, mockContact);
//        Assert.assertEquals(result.getBody().getId(), this.mockContactWithCustomer.getId());
//        Assert.assertEquals(result.getBody().getCompany(), this.mockContactWithCustomer.getCompany());
//        Assert.assertEquals(result.getBody().getCustomer(), this.mockContactWithCustomer.getCustomer());
//        Assert.assertEquals(result.getBody().getName(), this.mockContactWithCustomer.getName());
//        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContactWithCustomer.getJobTitle());
//        Assert.assertEquals(result.getBody().getImage(), this.mockContactWithCustomer.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//
//    @Test
//    public void testUpdateContact(){
//
//        ResponseEntity<Contact> mockResponse = new ResponseEntity<>(this.mockContact, HttpStatus.OK);
//        when(contactService.updateContact( 1L ,this.mockContact)).thenReturn(mockResponse);
//
//
//        ResponseEntity<Contact> result = contactController.updateContact(1L, this.mockContact);
//        Assert.assertEquals(result.getBody().getId(), this.mockContact.getId());
//        Assert.assertEquals(result.getBody().getCompany(), this.mockContact.getCompany());
//        Assert.assertEquals(result.getBody().getName(), this.mockContact.getName());
//        Assert.assertEquals(result.getBody().getJobTitle(), this.mockContact.getJobTitle());
//        Assert.assertEquals(result.getBody().getImage(), this.mockContact.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testDeleteContact(){
//        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(contactService.deleteContact( 1L )).thenReturn(mockResponse);
//        ResponseEntity<HttpStatus> result = contactController.deleteContact(1L);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testDeleteAllContactsOfCompany(){
//        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(contactService.deleteAllContactsOfCompany(1L)).thenReturn(mockResponse);
//        ResponseEntity<HttpStatus> result = contactController.deleteAllContactsOfCompany(1L);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//
//}