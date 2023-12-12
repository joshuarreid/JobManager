package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.LaborRepository;
import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.Status;
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


    public ResponseEntity<Iterable<Labor>> getAllLabors() {
        try {
            Iterable<Labor> labors = new ArrayList<>();
            labors = laborRepository.findAll();
            logger.info(labors.toString());
            if (!labors.iterator().hasNext()) {
                logger.info("No Labors Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Labors Successfully Fetched");
            return new ResponseEntity<>(labors, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Labor>> getAllLaborsByJobId(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            List<Labor> labors = new ArrayList<>();
            labors = laborRepository.findByLaborId(jobId);
            if (labors.isEmpty()) {
                logger.info("No Labor Found for JobId: " + jobId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(labors.size() + " Labors Successfully Found for JobId: " + jobId);
            return new ResponseEntity<>(labors, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Labor>> getAllLaborsByStatus(Status status) {
        try {
            List<Labor> labors = new ArrayList<>();
            labors = laborRepository.findByStatus(status);
            if (labors.isEmpty()) {
                logger.info("No Labor Found with Status: " + status.toString());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(labors.size() + " Labors Successfully Found with Status: " + status.toString());
            return new ResponseEntity<>(labors, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Labor> createLabor(Long jobId, Labor newLabor) {
        try {
            Labor labor = jobRepository.findById(jobId).map(job -> {
                newLabor.setJob(job);
                return laborRepository.save(newLabor);
            }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
            logger.info(labor);
            logger.info("Labor Successfully Created for JobId: " + jobId);
            return new ResponseEntity<>(labor, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Labor> updateLabor(Long id, Labor updatedLabor) {
        try {
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
            logger.info("Labor Successfully Updated");
            return new ResponseEntity<>(labor, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<HttpStatus> deleteLabor(Long id) {
        try {
            laborRepository.deleteById(id);
            logger.info("Labor Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Order>> deleteAllLaborsOfJob(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            laborRepository.deleteByJobId(jobId);
            logger.info("All Labors Successfully Deleted for JobId: " + jobId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
