package com.javatechie.aws.Service;


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

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CustomerServiceTests {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer mockCustomer;

    private Customer mockCustomer2;

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


        Customer mockCustomer2 = new Customer();
        mockCustomer2.setName("Mock Customer 2");
        mockCustomer2.setAddresses(addresses);
        mockCustomer2.setImage("http://photo.com");
        mockCustomer2.setId(1L);

        mockContact.setCustomer(mockCustomer);
        this.mockCustomer2 = mockCustomer2;

    }

    @Test
    public void testGetAllCustomers(){
        List<Customer> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockCustomer);
        ResponseEntity<Iterable<Customer>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(customerRepository.findAll()).thenReturn((Iterable<Customer>) mockResponseList);


        ResponseEntity<Iterable<Customer>> result = customerService.getAllCustomers();
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
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCustomer));


        ResponseEntity<Customer> result = customerService.getCustomerById(1L);
        Assert.assertEquals(result.getBody().getId(), this.mockCustomer.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockCustomer.getName());
        Assert.assertEquals(result.getBody().getImage(), this.mockCustomer.getImage());
        Assert.assertEquals(result.getBody().getAddresses(), this.mockCustomer.getAddresses());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testGetCustomerByIdInvalidId(){
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseEntity<Customer> result = customerService.getCustomerById(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
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
        when(customerRepository.save(mockCustomerNoId)).thenReturn(this.mockCustomer);


        ResponseEntity<Customer> result = customerService.createCustomer(mockCustomerNoId);
        Assert.assertEquals(result.getBody().getId(), this.mockCustomer.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockCustomer.getName());
        Assert.assertEquals(result.getBody().getImage(), this.mockCustomer.getImage());
        Assert.assertEquals(result.getBody().getAddresses(), this.mockCustomer.getAddresses());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testCreateCustomerNoName(){
        Customer mockCustomerNoId = new Customer();
        List<String> addresses = new ArrayList<>();
        addresses.add("4565 Rd");
        addresses.add("1234 Lane");
        mockCustomerNoId.setAddresses(addresses);
        mockCustomerNoId.setImage("http://photo.com");
        when(customerRepository.save(mockCustomerNoId)).thenReturn(this.mockCustomer);


        ResponseEntity<Customer> result = customerService.createCustomer(mockCustomerNoId);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateCustomer(){
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCustomer));
        when(customerRepository.save(this.mockCustomer2)).thenReturn(this.mockCustomer2);


        ResponseEntity<Customer> result = customerService.updateCustomer(1L, this.mockCustomer2);
        Assert.assertEquals(result.getBody().getId(), this.mockCustomer2.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockCustomer2.getName());
        Assert.assertEquals(result.getBody().getImage(), this.mockCustomer2.getImage());
        Assert.assertEquals(result.getBody().getAddresses(), this.mockCustomer2.getAddresses());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testUpdateCustomerInvalidId(){
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockCustomer));
        when(customerRepository.save(this.mockCustomer2)).thenReturn(this.mockCustomer2);


        ResponseEntity<Customer> result = customerService.updateCustomer(2L, this.mockCustomer2);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testDeleteCustomer(){
        when(customerRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<HttpStatus> result = customerService.deleteCustomer(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(customerRepository, times(1)).deleteById(1L);

    }

    @Test
    public void testDeleteCompanyInvalidId(){
        when(customerRepository.existsById(1L)).thenReturn(false);
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseEntity<HttpStatus> result = customerService.deleteCustomer(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

    }








}
