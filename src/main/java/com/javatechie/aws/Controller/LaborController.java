package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.LaborRepository;
import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.LaborService;
import com.javatechie.aws.common.ShipmentStatus;
import com.javatechie.aws.common.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LaborController {

    @Autowired
    LaborService laborService;

    private static final Logger logger = LogManager.getLogger(LaborController.class);

    @GetMapping("/labor")
    public ResponseEntity<Iterable<Labor>> getAllLabors() {
        logger.info("Fetching All Labors...");
        return laborService.getAllLabors();
    }

    @GetMapping("/job/{jobId}/labors")
    public ResponseEntity<List<Labor>> getAllLaborsByJobId(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Fetching Labors for jobId: " + jobId);
        return laborService.getAllLaborsByJobId(jobId);
    }

    @GetMapping("/labor/{status}")
    public ResponseEntity<List<Labor>> getAllLaborsByStatus(@PathVariable(value = "status") Status status) {
        logger.info("Fetching All Labors with status: " + status.toString());
        return laborService.getAllLaborsByStatus(status);
    }

    @PostMapping("/job/{jobId}/labors")
    public ResponseEntity<Labor> createLabor(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody Labor newLabor) {
        logger.info("Creating Labor...");
        logger.info(newLabor.toString());
        return laborService.createLabor(jobId, newLabor);
    }


    @PutMapping("/labor/{id}")
    public ResponseEntity<Labor> updateLabor(@PathVariable("id") long id, @RequestBody Labor updatedLabor) {
        logger.info("Updating LaborId: " + id);
        return laborService.updateLabor(id, updatedLabor);
    }

    @DeleteMapping("/labor/{id}")
    public ResponseEntity<HttpStatus> deleteLabor(@PathVariable("id") long id) {
        logger.info("Deleting LaborId: " + id);
        return laborService.deleteLabor(id);
    }

    @DeleteMapping("/job/{jobId}/labors")
    public ResponseEntity<List<Order>> deleteAllLaborsOfJob(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Deleting All Labors for JobId: " + jobId);
        return laborService.deleteAllLaborsOfJob(jobId);
    }





}
