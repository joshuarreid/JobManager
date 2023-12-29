package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.FileRepository;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.Model.File;
import com.javatechie.aws.common.exception.ResourceNotFoundException;
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
public class FileService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    FileRepository fileRepository;

    private static final Logger logger = LogManager.getLogger(FileService.class);


    public ResponseEntity<Object> getAllFilesByJobId(Long jobId) {
        List<File> files = new ArrayList<>();
        files = fileRepository.findByJobId(jobId);
        logger.info(files);
        return ResponseHandler.generateResponse(files);
    }

    public ResponseEntity<Object> createFile(Long jobId, File newFile) {
        File file = jobRepository.findById(jobId).map(job -> {
            newFile.setJob(job);
            return fileRepository.save(newFile);
        }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
        logger.info(file);
        return ResponseHandler.generateResponse(file);
    }


    public ResponseEntity<Object> updateFile(long id, File updatedFile) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File does not exist: id=" + id));

        file.setName(updatedFile.getName());
        file.setUploadedAt(updatedFile.getUploadedAt());
        file.setJob(updatedFile.getJob());
        fileRepository.save(file);
        logger.info(file);
        return ResponseHandler.generateResponse(file);
    }

    public ResponseEntity<Object> deleteFile(Long id) {
        fileRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteAllFilesOfJob(Long jobId) {
        fileRepository.deleteByJobId(jobId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }

}
