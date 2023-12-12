package com.javatechie.aws.Controller;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.javatechie.aws.Model.Company;
import com.javatechie.aws.Service.CompanyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CompanyControllerTests {
    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    private Company mockResponseCompany;

    @Before
    public void createMockCompany() {
        Company mockResponseCompany = new Company();
        mockResponseCompany.setName("Mock Company");
        mockResponseCompany.setAddress("1234 Mock Avenue");
        mockResponseCompany.setId(1L);
        this.mockResponseCompany = mockResponseCompany;
    }


    @Test
    public void testGetAllCompanies(){
        List<Company> mockResponseList = new ArrayList<>();
        mockResponseList.add(mockResponseCompany);
        ResponseEntity<List<Company>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(companyService.getAllCompanies()).thenReturn(mockResponse);


        ResponseEntity<List<Company>> result = companyService.getAllCompanies();
        Assert.assertEquals(result.getBody().get(0).getId(), this.mockResponseCompany.getId());
        Assert.assertEquals(result.getBody().get(0).getAddress(), this.mockResponseCompany.getAddress());
        Assert.assertEquals(result.getBody().get(0).getName(), this.mockResponseCompany.getName());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }



    @Test
    public void testGetCompanyById(){
        ResponseEntity<Company> mockResponse = new ResponseEntity<>(this.mockResponseCompany, HttpStatus.OK);
        when(companyService.getCompanyById(1L)).thenReturn(mockResponse);


        ResponseEntity<Company> result = companyService.getCompanyById(1L);
        Assert.assertEquals(result.getBody().getId(), this.mockResponseCompany.getId());
        Assert.assertEquals(result.getBody().getAddress(), this.mockResponseCompany.getAddress());
        Assert.assertEquals(result.getBody().getName(), this.mockResponseCompany.getName());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testCreateCompany(){
        Company mockRequestCompany = new Company();
        mockRequestCompany.setName("Mock Company");
        mockRequestCompany.setAddress("1234 Mock Avenue");
        ResponseEntity<Company> mockResponse = new ResponseEntity<>(this.mockResponseCompany, HttpStatus.OK);
        when(companyService.createCompany(mockRequestCompany)).thenReturn(mockResponse);


        ResponseEntity<Company> result = companyController.createCompany(mockRequestCompany);
        Assert.assertEquals(result.getBody().getId(), this.mockResponseCompany.getId());
        Assert.assertEquals(result.getBody().getAddress(), this.mockResponseCompany.getAddress());
        Assert.assertEquals(result.getBody().getName(), this.mockResponseCompany.getName());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testUpdateCompany(){
        Company mockRequestCompany = new Company();
        mockRequestCompany.setName("Mock Company");
        mockRequestCompany.setAddress("1234 Mock Avenue");
        mockRequestCompany.setId(1L);
        ResponseEntity<Company> mockResponse = new ResponseEntity<>(this.mockResponseCompany, HttpStatus.OK);
        when(companyService.updateCompany( 1L ,mockRequestCompany)).thenReturn(mockResponse);


        ResponseEntity<Company> result = companyController.updateCompany(1L, mockRequestCompany);
        Assert.assertEquals(result.getBody().getId(), this.mockResponseCompany.getId());
        Assert.assertEquals(result.getBody().getAddress(), this.mockResponseCompany.getAddress());
        Assert.assertEquals(result.getBody().getName(), this.mockResponseCompany.getName());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testDeleteCompany(){
        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.OK);
        when(companyService.deleteCompany( 1L )).thenReturn(mockResponse);
        ResponseEntity<HttpStatus> result = companyController.deleteCompany(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);

    }


}