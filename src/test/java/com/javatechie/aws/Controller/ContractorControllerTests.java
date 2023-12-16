//package com.javatechie.aws.Controller;
//
//import com.javatechie.aws.Model.*;
//import com.javatechie.aws.Service.ContractorService;
//import com.javatechie.aws.common.Status;
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
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//public class ContractorControllerTests {
//
//    @InjectMocks
//    private ContractorController contractorController;
//
//    @Mock
//    private ContractorService contractorService;
//
//    private Contractor mockContractor;
//
//    private Labor mockLabor;
//
//    private Job mockJob;
//
//    private Customer mockCustomer;
//
//    @Before
//    public void createMockContractor() {
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
//        Job mockJob = new Job();
//        mockJob.setCustomer(this.mockCustomer);
//        mockJob.setName("JOB-1");
//        mockJob.setEstimatedCost(100.00);
//        mockJob.setStatus(Status.INPROGRESS);
//        mockJob.setTotalCost(120.00);
//        mockJob.setId(1L);
//        this.mockJob = mockJob;
//
//        Labor mockLabor = new Labor();
//        mockLabor.setJob(this.mockJob);
//        mockLabor.setStatus(Status.INPROGRESS);
//        mockLabor.setTotalCost(20.0);
//        mockLabor.setTotalHours(2.0);
//        mockLabor.setDescription("Drive boxes to Coca-Cola HQ");
//        mockLabor.setId(1L);
//        this.mockLabor = mockLabor;
//
//        Contractor mockContractor = new Contractor();
//        mockContractor.setName("Joshua Reid");
//        mockContractor.setEmail("joshua.reid@company.com");
//        mockContractor.setStatus(Status.INPROGRESS);
//        mockContractor.setPhone(1234567643);
//        mockContractor.setImage("http://profilepicture.com");
//        mockContractor.setHoursWorked(2.0);
//        mockContractor.setPayRate(25.5);
//        mockContractor.setId(1L);
//        mockContractor.setLabor(this.mockLabor);
//        this.mockContractor = mockContractor;
//    }
//
//    @Test
//    public void testGetAllContractors(){
//        List<Contractor> mockResponseList = new ArrayList<>();
//        mockResponseList.add(this.mockContractor);
//        ResponseEntity<Iterable<Contractor>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
//        when(contractorService.getAllContractors()).thenReturn(mockResponse);
//
//
//        ResponseEntity<Iterable<Contractor>> result = contractorController.getAllContractors();
//        List<Contractor> body = (List) result.getBody();
//        Assert.assertEquals(body.get(0).getName(), this.mockContractor.getName());
//        Assert.assertEquals(body.get(0).getEmail(), this.mockContractor.getEmail());
//        Assert.assertEquals(body.get(0).getStatus(), this.mockContractor.getStatus());
//        Assert.assertEquals(body.get(0).getPhone(), this.mockContractor.getPhone());
//        Assert.assertEquals(body.get(0).getImage(), this.mockContractor.getImage());
//        Assert.assertEquals(body.get(0).getHoursWorked(), this.mockContractor.getHoursWorked());
//        Assert.assertEquals(body.get(0).getId(), this.mockContractor.getId());
//        Assert.assertEquals(body.get(0).getLabor(), this.mockContractor.getLabor());
//        Assert.assertEquals(body.get(0).getPayRate(), this.mockContractor.getPayRate());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//    @Test
//    public void testGetAllContractorsOfLabor(){
//        List<Contractor> mockResponseList = new ArrayList<>();
//        mockResponseList.add(this.mockContractor);
//        ResponseEntity<List<Contractor>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
//        when(contractorService.getAllContractorsByLaborId(1L)).thenReturn(mockResponse);
//
//
//        ResponseEntity<List<Contractor>> result = contractorController.getAllContractorsByLaborId(1L);
//        List<Contractor> body = (List) result.getBody();
//        Assert.assertEquals(body.get(0).getName(), this.mockContractor.getName());
//        Assert.assertEquals(body.get(0).getEmail(), this.mockContractor.getEmail());
//        Assert.assertEquals(body.get(0).getStatus(), this.mockContractor.getStatus());
//        Assert.assertEquals(body.get(0).getPhone(), this.mockContractor.getPhone());
//        Assert.assertEquals(body.get(0).getImage(), this.mockContractor.getImage());
//        Assert.assertEquals(body.get(0).getHoursWorked(), this.mockContractor.getHoursWorked());
//        Assert.assertEquals(body.get(0).getId(), this.mockContractor.getId());
//        Assert.assertEquals(body.get(0).getLabor(), this.mockContractor.getLabor());
//        Assert.assertEquals(body.get(0).getPayRate(), this.mockContractor.getPayRate());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//    @Test
//    public void testCreateContractor(){
//        Contractor mockContractorNoId = new Contractor();
//        mockContractorNoId.setName("Joshua Reid");
//        mockContractorNoId.setEmail("joshua.reid@company.com");
//        mockContractorNoId.setStatus(Status.INPROGRESS);
//        mockContractorNoId.setPhone(1234567643);
//        mockContractorNoId.setImage("http://profilepicture.com");
//        mockContractorNoId.setHoursWorked(2.0);
//        mockContractorNoId.setPayRate(25.5);
//        mockContractorNoId.setLabor(this.mockLabor);
//
//
//        ResponseEntity<Contractor> mockResponse = new ResponseEntity<>(this.mockContractor, HttpStatus.OK);
//        when(contractorService.createContractor(1L, mockContractorNoId)).thenReturn(mockResponse);
//
//
//        ResponseEntity<Contractor> result = contractorController.createContractor(1L, mockContractorNoId);
//        Assert.assertEquals(result.getBody().getId(), this.mockContractor.getId());
//        Assert.assertEquals(result.getBody().getName(), this.mockContractor.getName());
//        Assert.assertEquals(result.getBody().getEmail(), this.mockContractor.getEmail());
//        Assert.assertEquals(result.getBody().getStatus(), this.mockContractor.getStatus());
//        Assert.assertEquals(result.getBody().getPhone(), this.mockContractor.getPhone());
//        Assert.assertEquals(result.getBody().getImage(), this.mockContractor.getImage());
//        Assert.assertEquals(result.getBody().getHoursWorked(), this.mockContractor.getHoursWorked());
//        Assert.assertEquals(result.getBody().getLabor(), this.mockContractor.getLabor());
//        Assert.assertEquals(result.getBody().getPayRate(), this.mockContractor.getPayRate());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//    @Test
//    public void testUpdateContractor(){
//        ResponseEntity<Contractor> mockResponse = new ResponseEntity<>(this.mockContractor, HttpStatus.OK);
//        when(contractorService.updateContractor( 1L ,this.mockContractor)).thenReturn(mockResponse);
//
//
//        ResponseEntity<Contractor> result = contractorController.updateContractor(1L, this.mockContractor);
//        Assert.assertEquals(result.getBody().getId(), this.mockContractor.getId());
//        Assert.assertEquals(result.getBody().getName(), this.mockContractor.getName());
//        Assert.assertEquals(result.getBody().getEmail(), this.mockContractor.getEmail());
//        Assert.assertEquals(result.getBody().getStatus(), this.mockContractor.getStatus());
//        Assert.assertEquals(result.getBody().getPhone(), this.mockContractor.getPhone());
//        Assert.assertEquals(result.getBody().getImage(), this.mockContractor.getImage());
//        Assert.assertEquals(result.getBody().getHoursWorked(), this.mockContractor.getHoursWorked());
//        Assert.assertEquals(result.getBody().getLabor(), this.mockContractor.getLabor());
//        Assert.assertEquals(result.getBody().getPayRate(), this.mockContractor.getPayRate());
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//    @Test
//    public void testDeleteContractor(){
//        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(contractorService.deleteContractor( 1L )).thenReturn(mockResponse);
//        ResponseEntity<HttpStatus> result = contractorController.deleteContractor(1L);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//
//    }
//
//    @Test
//    public void testDeleteAllContractorsOfLabor(){
//        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(contractorService.deleteAllContractorsOfLabor(1L)).thenReturn(mockResponse);
//        ResponseEntity<HttpStatus> result = contractorController.deleteAllContractorsOfLabor(1L);
//        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }
//
//}