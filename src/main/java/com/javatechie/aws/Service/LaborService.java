package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.LaborRepository;
import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.ShipmentStatus;
import com.javatechie.aws.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LaborService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    LaborRepository laborRepository;


    public ResponseEntity<Iterable<Labor>> getAllLabors() {
        return new ResponseEntity<>(laborRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<List<Labor>> getAllLaborsByJobId(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Not found Job with id = " + jobId);
        }
        List<Labor> labors = laborRepository.findByJobId(jobId);
        return new ResponseEntity<>(labors, HttpStatus.OK);
    }

    public ResponseEntity<List<Labor>> getAllLaborsByStatus(Status status) {

        List<Labor> labors = laborRepository.findByStatus(status);
        return new ResponseEntity<>(labors, HttpStatus.OK);
    }


    public ResponseEntity<Labor> createLabor(Long jobId, Labor newLabor) {
        Labor labor = jobRepository.findById(jobId).map(job -> {
            newLabor.setJob(job);
            return laborRepository.save(newLabor);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Job with id = " + jobId));

        return new ResponseEntity<>(labor, HttpStatus.CREATED);
    }



    public ResponseEntity<Labor> updateLabor(Long id, Labor updatedLabor) {
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LaborId " + id + "not found"));

        if (updatedLabor.getDescription() != null) labor.setDescription(updatedLabor.getDescription());
        if (updatedLabor.getStatus() != null) labor.setStatus(updatedLabor.getStatus());
        if (updatedLabor.getTotalCost() != null) labor.setTotalCost(updatedLabor.getTotalCost());
        if (updatedLabor.getTotalHours() != null) labor.setTotalHours(updatedLabor.getTotalHours());
        if (updatedLabor.getDescription() != null) labor.setDescription(updatedLabor.getDescription());
        if (updatedLabor.getCompletedAt() != null) labor.setCompletedAt(updatedLabor.getCompletedAt());
        if (updatedLabor.getJob() != null) labor.setJob(updatedLabor.getJob());
        return new ResponseEntity<>(laborRepository.save(labor), HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> deleteLabor(Long id) {
        laborRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<List<Order>> deleteAllLaborsOfJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Not found Job with id = " + jobId);
        }

        laborRepository.deleteByJobId(jobId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
