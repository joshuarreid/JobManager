package com.javatechie.aws.Controller;

import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.File;
import com.javatechie.aws.Service.ExpenseService;
import com.javatechie.aws.Service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    FileService fileService;

    private static final Logger logger = LogManager.getLogger(FileController.class);


    @GetMapping("/job/{jobId}/files")
    public ResponseEntity<Object> getAllFilesByJobId(@PathVariable(value = "jobId") Long jobId) {
        return fileService.getAllFilesByJobId(jobId);
    }

    @PostMapping("/job/{jobId}/files")
    public ResponseEntity<Object> createFile(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody File newFile) {
        return fileService.createFile(jobId, newFile);
    }


    @PutMapping("/file/{id}")
    public ResponseEntity<Object> updateFile(@PathVariable("id") Long id, @RequestBody File updatedFile) {
        return fileService.updateFile(id, updatedFile);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable("id") Long id) {
        return fileService.deleteFile(id);
    }

    @DeleteMapping("/job/{jobId}/files")
    public ResponseEntity<Object> deleteAllFilesOfJob(@PathVariable(value = "jobId") Long jobId) {
        return fileService.deleteAllFilesOfJob(jobId);
    }





}
