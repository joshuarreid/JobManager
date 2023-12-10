package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.LaborRepository;
import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.LaborService;
import com.javatechie.aws.common.ShipmentStatus;
import com.javatechie.aws.common.Status;
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

    @GetMapping("/labor")
    public ResponseEntity<Iterable<Labor>> getAllLabors() {
        return laborService.getAllLabors();
    }

    @GetMapping("/job/{jobId}/labors")
    public ResponseEntity<List<Labor>> getAllLaborsByJobId(@PathVariable(value = "jobId") Long jobId) {
        return laborService.getAllLaborsByJobId(jobId);
    }

    @GetMapping("/labor/{status}")
    public ResponseEntity<List<Labor>> getAllLaborsByStatus(@PathVariable(value = "status") Status status) {
        return laborService.getAllLaborsByStatus(status);
    }

    @PostMapping("/job/{jobId}/labors")
    public ResponseEntity<Labor> createLabor(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody Labor newLabor) {
        return laborService.createLabor(jobId, newLabor);
    }


    @PutMapping("/labor/{id}")
    public ResponseEntity<Labor> updateLabor(@PathVariable("id") long id, @RequestBody Labor updatedLabor) {
        return laborService.updateLabor(id, updatedLabor);
    }

    @DeleteMapping("/labor/{id}")
    public ResponseEntity<HttpStatus> deleteLabor(@PathVariable("id") long id) {
        return laborService.deleteLabor(id);
    }

    @DeleteMapping("/job/{jobId}/labors")
    public ResponseEntity<List<Order>> deleteAllLaborsOfJob(@PathVariable(value = "jobId") Long jobId) {
        return laborService.deleteAllLaborsOfJob(jobId);
    }





}
