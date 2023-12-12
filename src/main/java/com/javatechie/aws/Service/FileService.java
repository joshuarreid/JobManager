package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.ExpenseRepository;
import com.javatechie.aws.DAO.FileRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.Expense;
import com.javatechie.aws.Model.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    FileRepository fileRepository;

    private static final Logger logger = LogManager.getLogger(FileService.class);


    public ResponseEntity<List<File>> getAllFilesByJobId(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            List<File> files = new ArrayList<>();
            files = fileRepository.findByJobId(jobId);
            if (files.isEmpty()) {
                logger.info("No Files Found for JobId: " + jobId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(files.size() + " Files Successfully Found for JobId: " + jobId);
            return new ResponseEntity<>(files, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<File> createFile(Long jobId, File newFile) {
        try {
            File file = jobRepository.findById(jobId).map(job -> {
                newFile.setJob(job);
                return fileRepository.save(newFile);
            }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
            logger.info(file);
            logger.info("File Successfully Created for JobId: " + jobId);
            return new ResponseEntity<>(file, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<File> updateFile(long id, File updatedFile) {
        try {
            File file = fileRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("File does not exist: id=" + id));

            file.setName(updatedFile.getName());
            file.setUploadedAt(updatedFile.getUploadedAt());
            file.setJob(updatedFile.getJob());
            fileRepository.save(file);
            logger.info(file);
            logger.info("File Successfully Updated");
            return new ResponseEntity<>(file, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteFile(Long id) {
        try {
            fileRepository.deleteById(id);
            logger.info("File Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<File>> deleteAllFilesOfJob(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            fileRepository.deleteByJobId(jobId);
            logger.info("All Files Successfully Deleted for JobId: " + jobId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
