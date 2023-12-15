//package com.javatechie.aws.Service;
//
//import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
//import com.javatechie.aws.Controller.CompanyController;
//import com.javatechie.aws.DAO.CompanyRepository;
//import com.javatechie.aws.Model.Company;
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
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//public class CompanyServiceTests {
//    @InjectMocks
//    private CompanyService companyService;
//
//    @Mock
//    private CompanyRepository companyRepository;
//
//    private Company mockResponseCompany;
//
//    private Company mockResponseCompany2;
//
//    private ResourceNotFoundException resourceNotFoundException;
//
//    @Before
//    public void createMockCompany() {
//        Company mockResponseCompany = new Company();
//        mockResponseCompany.setName("Mock Company");
//        mockResponseCompany.setAddress("1234 Mock Avenue");
//        mockResponseCompany.setImage("http://photo.com");
//        mockResponseCompany.setId(1L);
//        this.mockResponseCompany = mockResponseCompany;
//
//        Company mockResponseCompany2 = new Company();
//        mockResponseCompany2.setName("Mock Company 2");
//        mockResponseCompany2.setAddress("2345 Mock St");
//        mockResponseCompany2.setImage("http://photo.com");
//        mockResponseCompany2.setId(1L);
//        this.mockResponseCompany2 = mockResponseCompany2;
//    }
//
//
////    @Test
////    public void testGetAllCompanies(){
////        List<Company> mockResponseList = new ArrayList<>();
////        mockResponseList.add(mockResponseCompany);
////        when(companyRepository.findAll()).thenReturn((Iterable<Company>) mockResponseList);
////
////
////        ResponseEntity<Object> result = companyService.getAllCompanies();
////        Assert.assertEquals(result.getBody().get(0).getId(), this.mockResponseCompany.getId());
////        Assert.assertEquals(result.getBody().get(0).getAddress(), this.mockResponseCompany.getAddress());
////        Assert.assertEquals(result.getBody().get(0).getName(), this.mockResponseCompany.getName());
////        Assert.assertEquals(result.getBody().get(0).getImage(), this.mockResponseCompany.getImage());
////        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
////
////    }
////
////    @Test
////    public void testGetAllCompaniesEmpty(){
////        List<Company> mockResponseList = new ArrayList<>();
////        when(companyRepository.findAll()).thenReturn((Iterable<Company>) mockResponseList);
////        ResponseEntity<Object> result = companyService.getAllCompanies();
////        Assert.assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
////
////    }
//
//
//
////    @Test
////    public void testGetCompanyById(){
////        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockResponseCompany));
////
////        ResponseEntity<Company> result = companyService.getCompanyById(1L);
////        Assert.assertEquals(result.getBody().getId(), this.mockResponseCompany.getId());
////        Assert.assertEquals(result.getBody().getAddress(), this.mockResponseCompany.getAddress());
////        Assert.assertEquals(result.getBody().getName(), this.mockResponseCompany.getName());
////        Assert.assertEquals(result.getBody().getImage(), this.mockResponseCompany.getImage());
////        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
////
////    }
//
////    @Test
////    public void testGetCompanyByIdInvalidId(){
////        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));
////
////        ResponseEntity<Company> result = companyService.getCompanyById(1L);
////        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
////    }
//
//    @Test
//    public void testCreateCompany(){
//        Company mockRequestCompany = new Company();
//        mockRequestCompany.setName("Mock Company");
//        mockRequestCompany.setAddress("1234 Mock Avenue");
//        mockRequestCompany.setImage("http://photo.com");
//        when(companyRepository.save(mockRequestCompany)).thenReturn(this.mockResponseCompany);
//
//
//        ResponseEntity<Company> result = companyService.createCompany(mockRequestCompany);
//        Assert.assertEquals(result.getBody().getId(), this.mockResponseCompany.getId());
//        Assert.assertEquals(result.getBody().getAddress(), this.mockResponseCompany.getAddress());
//        Assert.assertEquals(result.getBody().getName(), this.mockResponseCompany.getName());
//        Assert.assertEquals(result.getBody().getImage(), this.mockResponseCompany.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
//
//    }
//
//    @Test
//    public void testUpdateCompany(){
//        when(companyRepository.existsById(1L)).thenReturn(true);
//        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockResponseCompany));
//        when(companyRepository.save(this.mockResponseCompany2)).thenReturn(this.mockResponseCompany2);
//
//
//        ResponseEntity<Company> result = companyService.updateCompany(1L, this.mockResponseCompany2);
//        Assert.assertEquals(result.getBody().getId(), this.mockResponseCompany2.getId());
//        Assert.assertEquals(result.getBody().getAddress(), this.mockResponseCompany2.getAddress());
//        Assert.assertEquals(result.getBody().getName(), this.mockResponseCompany2.getName());
//        Assert.assertEquals(result.getBody().getImage(), this.mockResponseCompany2.getImage());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testUpdateCompanyInvalidId(){
//        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockResponseCompany));
//        when(companyRepository.save(this.mockResponseCompany2)).thenReturn(this.mockResponseCompany2);
//
//
//        ResponseEntity<Company> result = companyService.updateCompany(2L, this.mockResponseCompany2);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
//
//    }
////
//    @Test
//    public void testDeleteCompany(){
//        when(companyRepository.existsById(1L)).thenReturn(true);
//
//        ResponseEntity<HttpStatus> result = companyService.deleteCompany(1L);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//        verify(companyRepository, times(1)).deleteById(1L);
//
//    }
//
//    @Test
//    public void testDeleteCompanyInvalidId(){
//        when(companyRepository.existsById(1L)).thenReturn(false);
//        when(companyRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));
//
//        ResponseEntity<HttpStatus> result = companyService.deleteCompany(1L);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
//
//    }
//
//
//
//}