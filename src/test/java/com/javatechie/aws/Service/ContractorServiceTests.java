package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.ContractorRepository;
import com.javatechie.aws.DAO.LaborRepository;
import com.javatechie.aws.Model.*;
import com.javatechie.aws.common.Status;
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
public class ContractorServiceTests {

    @InjectMocks
    private ContractorService contractorService;

    @Mock
    private ContractorRepository contractorRepository;

    @Mock
    private LaborRepository laborRepository;

    private Contractor mockContractor;

    private Contractor mockContractor2;

    private Labor mockLabor;

    private Job mockJob;

    private Customer mockCustomer;


    @Before
    public void createMockContractor() {
        List<String> addresses = new ArrayList<>();
        addresses.add("4565 Rd");
        addresses.add("1234 Lane");
        Customer mockCustomer = new Customer();
        mockCustomer.setName("Mock Customer 1");
        mockCustomer.setAddresses(addresses);
        mockCustomer.setImage("http://photo.com");
        mockCustomer.setId(1L);
        this.mockCustomer = mockCustomer;

        Job mockJob = new Job();
        mockJob.setCustomer(this.mockCustomer);
        mockJob.setName("JOB-1");
        mockJob.setEstimatedCost(100.00);
        mockJob.setStatus(Status.INPROGRESS);
        mockJob.setTotalCost(120.00);
        mockJob.setId(1L);
        this.mockJob = mockJob;

        Labor mockLabor = new Labor();
        mockLabor.setJob(this.mockJob);
        mockLabor.setStatus(Status.INPROGRESS);
        mockLabor.setTotalCost(20.0);
        mockLabor.setTotalHours(2.0);
        mockLabor.setDescription("Drive boxes to Coca-Cola HQ");
        mockLabor.setId(1L);
        this.mockLabor = mockLabor;

        Contractor mockContractor = new Contractor();
        mockContractor.setName("Joshua Reid");
        mockContractor.setEmail("joshua.reid@company.com");
        mockContractor.setStatus(Status.INPROGRESS);
        mockContractor.setPhone(1234567643);
        mockContractor.setImage("http://profilepicture.com");
        mockContractor.setHoursWorked(2.0);
        mockContractor.setPayRate(25.5);
        mockContractor.setId(1L);
        mockContractor.setLabor(this.mockLabor);
        this.mockContractor = mockContractor;

        Contractor mockContractor2 = new Contractor();
        mockContractor2.setName("Joshua Reid");
        mockContractor2.setEmail("joshua.reid@company.com");
        mockContractor2.setStatus(Status.COMPLETE);
        mockContractor2.setPhone(1234567643);
        mockContractor2.setImage("http://profilepicture.com");
        mockContractor2.setHoursWorked(2.0);
        mockContractor2.setPayRate(25.5);
        mockContractor2.setId(1L);
        mockContractor2.setLabor(this.mockLabor);
        this.mockContractor2 = mockContractor2;
    }

    @Test
    public void testGetAllContractors(){
        List<Contractor> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContractor);
        when(contractorRepository.findAll()).thenReturn(mockResponseList);


        ResponseEntity<Iterable<Contractor>> result = contractorService.getAllContractors();
        List<Contractor> body = (List) result.getBody();
        Assert.assertEquals(body.get(0).getName(), this.mockContractor.getName());
        Assert.assertEquals(body.get(0).getEmail(), this.mockContractor.getEmail());
        Assert.assertEquals(body.get(0).getStatus(), this.mockContractor.getStatus());
        Assert.assertEquals(body.get(0).getPhone(), this.mockContractor.getPhone());
        Assert.assertEquals(body.get(0).getImage(), this.mockContractor.getImage());
        Assert.assertEquals(body.get(0).getHoursWorked(), this.mockContractor.getHoursWorked());
        Assert.assertEquals(body.get(0).getId(), this.mockContractor.getId());
        Assert.assertEquals(body.get(0).getLabor(), this.mockContractor.getLabor());
        Assert.assertEquals(body.get(0).getPayRate(), this.mockContractor.getPayRate());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetAllContractorsEmpty(){
        List<Contractor> mockResponseList = new ArrayList<>();
        when(contractorRepository.findAll()).thenReturn(mockResponseList);

        ResponseEntity<Iterable<Contractor>> result = contractorService.getAllContractors();
        Assert.assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetAllContractorsOfLabor(){
        List<Contractor> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContractor);
        when(laborRepository.existsById(1L)).thenReturn(true);
        when(contractorRepository.findByLaborId(1L)).thenReturn(mockResponseList);

        ResponseEntity<List<Contractor>> result = contractorService.getAllContractorsByLaborId(1L);
        List<Contractor> body = (List) result.getBody();
        Assert.assertEquals(body.get(0).getName(), this.mockContractor.getName());
        Assert.assertEquals(body.get(0).getEmail(), this.mockContractor.getEmail());
        Assert.assertEquals(body.get(0).getStatus(), this.mockContractor.getStatus());
        Assert.assertEquals(body.get(0).getPhone(), this.mockContractor.getPhone());
        Assert.assertEquals(body.get(0).getImage(), this.mockContractor.getImage());
        Assert.assertEquals(body.get(0).getHoursWorked(), this.mockContractor.getHoursWorked());
        Assert.assertEquals(body.get(0).getId(), this.mockContractor.getId());
        Assert.assertEquals(body.get(0).getLabor(), this.mockContractor.getLabor());
        Assert.assertEquals(body.get(0).getPayRate(), this.mockContractor.getPayRate());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetAllContractorsByLaborIdInvalidId(){
        List<Contractor> mockResponseList = new ArrayList<>();
        mockResponseList.add(this.mockContractor);
        ResponseEntity<Iterable<Contractor>> mockResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);
        when(laborRepository.existsById(1L)).thenReturn(false);
        when(contractorRepository.findByLaborId(1L)).thenReturn((List<Contractor>) mockResponseList);

        ResponseEntity<List<Contractor>> result = contractorService.getAllContractorsByLaborId(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateContractor(){
        Contractor mockContractorNoId = new Contractor();
        mockContractorNoId.setName("Joshua Reid");
        mockContractorNoId.setEmail("joshua.reid@company.com");
        mockContractorNoId.setStatus(Status.INPROGRESS);
        mockContractorNoId.setPhone(1234567643);
        mockContractorNoId.setImage("http://profilepicture.com");
        mockContractorNoId.setHoursWorked(2.0);
        mockContractorNoId.setPayRate(25.5);
        mockContractorNoId.setLabor(this.mockLabor);

        when(contractorRepository.save(mockContractorNoId)).thenReturn(this.mockContractor);
        when(laborRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockLabor));
        when(contractorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContractor));
        when(laborRepository.existsById(1L)).thenReturn(true);
        ResponseEntity<Contractor> result = contractorService.createContractor(1L, mockContractorNoId);
        Assert.assertEquals(result.getBody().getId(), this.mockContractor.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockContractor.getName());
        Assert.assertEquals(result.getBody().getEmail(), this.mockContractor.getEmail());
        Assert.assertEquals(result.getBody().getStatus(), this.mockContractor.getStatus());
        Assert.assertEquals(result.getBody().getPhone(), this.mockContractor.getPhone());
        Assert.assertEquals(result.getBody().getImage(), this.mockContractor.getImage());
        Assert.assertEquals(result.getBody().getHoursWorked(), this.mockContractor.getHoursWorked());
        Assert.assertEquals(result.getBody().getLabor(), this.mockContractor.getLabor());
        Assert.assertEquals(result.getBody().getPayRate(), this.mockContractor.getPayRate());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testCreateContractorInvalidLaborId(){
        Contractor mockContractorNoId = new Contractor();
        mockContractorNoId.setName("Joshua Reid");
        mockContractorNoId.setEmail("joshua.reid@company.com");
        mockContractorNoId.setStatus(Status.INPROGRESS);
        mockContractorNoId.setPhone(1234567643);
        mockContractorNoId.setImage("http://profilepicture.com");
        mockContractorNoId.setHoursWorked(2.0);
        mockContractorNoId.setPayRate(25.5);


        when(contractorRepository.save(mockContractorNoId)).thenReturn(this.mockContractor);
        when(laborRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockLabor));
        when(contractorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContractor));
        when(laborRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Contractor> result = contractorService.createContractor(1L, mockContractorNoId);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateContractor(){
        when(contractorRepository.existsById(1L)).thenReturn(true);
        when(contractorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContractor));
        when(contractorRepository.save(this.mockContractor2)).thenReturn(this.mockContractor2);


        ResponseEntity<Contractor> result = contractorService.updateContractor(1L, this.mockContractor2);
        Assert.assertEquals(result.getBody().getId(), this.mockContractor2.getId());
        Assert.assertEquals(result.getBody().getName(), this.mockContractor2.getName());
        Assert.assertEquals(result.getBody().getEmail(), this.mockContractor2.getEmail());
        Assert.assertEquals(result.getBody().getStatus(), this.mockContractor2.getStatus());
        Assert.assertEquals(result.getBody().getPhone(), this.mockContractor2.getPhone());
        Assert.assertEquals(result.getBody().getImage(), this.mockContractor2.getImage());
        Assert.assertEquals(result.getBody().getHoursWorked(), this.mockContractor2.getHoursWorked());
        Assert.assertEquals(result.getBody().getLabor(), this.mockContractor2.getLabor());
        Assert.assertEquals(result.getBody().getPayRate(), this.mockContractor2.getPayRate());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateContractorInvalidId(){
        when(contractorRepository.existsById(1L)).thenReturn(false);
        when(contractorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(this.mockContractor));
        when(contractorRepository.save(mockContractor2)).thenReturn(this.mockContractor2);


        ResponseEntity<Contractor> result = contractorService.updateContractor(2L, mockContractor2);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testDeleteContractor(){
        when(contractorRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<HttpStatus> result = contractorService.deleteContractor(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(contractorRepository, times(1)).deleteById(1L);

    }

    @Test
    public void testDeleteContractorInvalidId(){
        when(contractorRepository.existsById(1L)).thenReturn(false);
        when(contractorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));

        ResponseEntity<HttpStatus> result = contractorService.deleteContractor(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testDeleteAllContractorsOfLabor(){
        when(laborRepository.existsById(1L)).thenReturn(true);


        ResponseEntity<HttpStatus> result = contractorService.deleteAllContractorsOfLabor(1L);
        verify(contractorRepository, times(1)).deleteByLaborId(1L);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }





}