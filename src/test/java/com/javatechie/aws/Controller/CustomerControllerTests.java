package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.*;
import com.javatechie.aws.Service.CustomerService;
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

@RunWith(SpringRunner.class)
public class CustomerControllerTests {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;



    private Customer mockCustomer;

    private Contact mockContact;

    private Company mockCompany;



    @Before
    public void createMockCustomer() {
        Company mockCompany = new Company();
        mockCompany.setName("Mock Company");
        mockCompany.setAddress("1234 Mock Avenue");
        mockCompany.setImage("http://photo.com");
        mockCompany.setId(1L);
        this.mockCompany = mockCompany;


        Contact mockContact = new Contact();
        mockContact.setName("John Mocker");
        mockContact.setJobTitle("Head Mock Advisor");
        mockContact.setCompany(this.mockCompany);
        mockContact.setImage("http://photo.com");
        mockContact.setId(1L);
        this.mockContact = mockContact;

        List<String> addresses = new ArrayList<>();
        addresses.add("4565 Rd");
        addresses.add("1234 Lane");
        Customer mockCustomer = new Customer();
        mockCustomer.setName("Mock Customer 1");
        mockCustomer.setAddresses(addresses);
        mockCustomer.setImage("http://photo.com");
        mockCustomer.setId(1L);

        mockContact.setCustomer(mockCustomer);
        this.mockCustomer = mockCustomer;

    }

    @Test
    public void testGetAllCustomers(){
        List<Customer> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockCustomer);
        ResponseEntity<Iterable<Customer>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(customerService.getAllCustomers()).thenReturn(mockResponse);


        ResponseEntity<Iterable<Customer>> result = customerController.getAllCustomers();
        List<Customer> body = (List) result.getBody();
        Assert.assertEquals(body.get(0).getName(), this.mockCustomer.getName());
        Assert.assertEquals(body.get(0).getAddresses(), this.mockCustomer.getAddresses());
        Assert.assertEquals(body.get(0).getImage(), this.mockCustomer.getImage());
        Assert.assertEquals(body.get(0).getId(), this.mockCustomer.getId());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetCustomerById(){
        ResponseEntity<Customer> mockResponse = new ResponseEntity<>(this.mockCustomer, HttpStatus.OK);
        when(customerService.getCustomerById(1L)).thenReturn(mockResponse);

        ResponseEntity<Customer> result = customerController.getCustomerById(1L);
        Assert.assertEquals(result.getBody().getId(), this.mockCustomer.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockCustomer.getName());
        Assert.assertEquals(result.getBody().getImage(), this.mockCustomer.getImage());
        Assert.assertEquals(result.getBody().getAddresses(), this.mockCustomer.getAddresses());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testCreateCustomer(){
        Customer mockCustomerNoId = new Customer();
        List<String> addresses = new ArrayList<>();
        addresses.add("4565 Rd");
        addresses.add("1234 Lane");
        mockCustomerNoId.setName("Mock Customer 1");
        mockCustomerNoId.setAddresses(addresses);
        mockCustomerNoId.setImage("http://photo.com");


        ResponseEntity<Customer> mockResponse = new ResponseEntity<>(this.mockCustomer, HttpStatus.OK);
        when(customerService.createCustomer(mockCustomerNoId)).thenReturn(mockResponse);


        ResponseEntity<Customer> result = customerController.createCustomer(mockCustomerNoId);
        Assert.assertEquals(result.getBody().getId(), this.mockCustomer.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockCustomer.getName());
        Assert.assertEquals(result.getBody().getImage(), this.mockCustomer.getImage());
        Assert.assertEquals(result.getBody().getAddresses(), this.mockCustomer.getAddresses());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateCustomer(){
        ResponseEntity<Customer> mockResponse = new ResponseEntity<>(this.mockCustomer, HttpStatus.OK);
        when(customerService.updateCustomer( 1L ,this.mockCustomer)).thenReturn(mockResponse);


        ResponseEntity<Customer> result = customerController.updateCustomer(1L, this.mockCustomer);
        Assert.assertEquals(result.getBody().getId(), this.mockCustomer.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockCustomer.getName());
        Assert.assertEquals(result.getBody().getImage(), this.mockCustomer.getImage());
        Assert.assertEquals(result.getBody().getAddresses(), this.mockCustomer.getAddresses());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testDeleteCustomer(){
        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(customerService.deleteCustomer( 1L )).thenReturn(mockResponse);
        ResponseEntity<HttpStatus> result = customerController.deleteCustomer(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }


}