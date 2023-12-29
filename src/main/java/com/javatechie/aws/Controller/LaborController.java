package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Service.LaborService;
import com.javatechie.aws.common.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LaborController {

    @Autowired
    LaborService laborService;

    private static final Logger logger = LogManager.getLogger(LaborController.class);

    @GetMapping("/labor")
    public ResponseEntity<Object> getAllLabors() {
        return laborService.getAllLabors();
    }

    @GetMapping("/job/{jobId}/labors")
    public ResponseEntity<Object> getAllLaborsByJobId(@PathVariable(value = "jobId") Long jobId) {
        return laborService.getAllLaborsByJobId(jobId);
    }

    @GetMapping("/labor/{status}")
    public ResponseEntity<Object> getAllLaborsByStatus(@PathVariable(value = "status") Status status) {
        return laborService.getAllLaborsByStatus(status);
    }

    @PostMapping("/job/{jobId}/labors")
    public ResponseEntity<Object> createLabor(@PathVariable(value = "jobId") Long jobId,
                                              @RequestBody Labor newLabor) {
        return laborService.createLabor(jobId, newLabor);
    }


    @PutMapping("/labor/{id}")
    public ResponseEntity<Object> updateLabor(@PathVariable("id") long id, @RequestBody Labor updatedLabor) {
        return laborService.updateLabor(id, updatedLabor);
    }

    @DeleteMapping("/labor/{id}")
    public ResponseEntity<Object> deleteLabor(@PathVariable("id") long id) {
        return laborService.deleteLabor(id);
    }

    @DeleteMapping("/job/{jobId}/labors")
    public ResponseEntity<Object> deleteAllLaborsOfJob(@PathVariable(value = "jobId") Long jobId) {
        return laborService.deleteAllLaborsOfJob(jobId);
    }


}
