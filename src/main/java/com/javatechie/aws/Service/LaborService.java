package com.javatechie.aws.Service;

import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.LaborRepository;
import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.Status;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class LaborService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    LaborRepository laborRepository;

    private static final Logger logger = LogManager.getLogger(LaborService.class);


    public ResponseEntity<Object> getAllLabors() {
        Iterable<Labor> labors = new ArrayList<>();
        labors = laborRepository.findAll();
        logger.info(labors);
        return ResponseHandler.generateResponse(labors);
    }


    public ResponseEntity<Object> getAllLaborsByJobId(Long jobId) {
        List<Labor> labors = new ArrayList<>();
        labors = laborRepository.findByJobId(jobId);
        logger.info(labors);
        return ResponseHandler.generateResponse(labors);
    }

    public ResponseEntity<Object> getAllLaborsByStatus(Status status) {
        List<Labor> labors = new ArrayList<>();
        labors = laborRepository.findByStatus(status);
        logger.info(labors);
        return ResponseHandler.generateResponse(labors);
    }


    public ResponseEntity<Object> createLabor(Long jobId, Labor newLabor) {
        Labor labor = jobRepository.findById(jobId).map(job -> {
            newLabor.setJob(job);
            return laborRepository.save(newLabor);
        }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
        logger.info(labor);
        return ResponseHandler.generateResponse(labor);
    }



    public ResponseEntity<Object> updateLabor(Long id, Labor updatedLabor) {
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Labor does not exist: id=" + id));

        labor.setDescription(updatedLabor.getDescription());
        labor.setStatus(updatedLabor.getStatus());
        labor.setTotalCost(updatedLabor.getTotalCost());
        labor.setTotalHours(updatedLabor.getTotalHours());
        labor.setDescription(updatedLabor.getDescription());
        labor.setCompletedAt(updatedLabor.getCompletedAt());
        labor.setJob(updatedLabor.getJob());
        laborRepository.save(labor);
        logger.info(labor);
        return ResponseHandler.generateResponse(labor);
    }


    public ResponseEntity<Object> deleteLabor(Long id) {
        laborRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


    public ResponseEntity<Object> deleteAllLaborsOfJob(Long jobId) {
        laborRepository.deleteByJobId(jobId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }
}
