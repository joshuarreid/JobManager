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
    public ResponseEntity<List<File>> getAllFilesByJobId(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Fetching Files for jobId: " + jobId);
        return fileService.getAllFilesByJobId(jobId);
    }

    @PostMapping("/job/{jobId}/files")
    public ResponseEntity<File> createFile(@PathVariable(value = "jobId") Long jobId,
                                             @RequestBody File newFile) {
        logger.info("Creating File...");
        logger.info(newFile.toString());
        return fileService.createFile(jobId, newFile);
    }


    @PutMapping("/file/{id}")
    public ResponseEntity<File> updateFile(@PathVariable("id") Long id, @RequestBody File updatedFile) {
        logger.info("Updating FileId: " + id);
        return fileService.updateFile(id, updatedFile);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("id") Long id) {
        logger.info("Deleting FileId: " + id);
        return fileService.deleteFile(id);
    }

    @DeleteMapping("/job/{jobId}/files")
    public ResponseEntity<List<File>> deleteAllFilesOfJob(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Deleting All Files for JobId: " + jobId);
        return fileService.deleteAllFilesOfJob(jobId);
    }





}
