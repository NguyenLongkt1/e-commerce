package com.example.fileservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.common.exception.BussinessException;
import com.example.fileservice.entity.FileManagement;
import com.example.fileservice.repository.FileManagementRepository;
import com.example.fileservice.service.FileService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.endpointUrl}")
    private String endpointUrl;


    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FileManagementRepository repository;

    @Override
    public String uploadSingleFile(MultipartFile multipartFile) throws BussinessException {
        String fileUrl;
        try {
            String fileName = generateFileName(multipartFile);
            File convertFile = convertMultipartFileToFile(multipartFile);
            amazonS3.putObject(bucketName, fileName, convertFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            ObjectMetadata metadata = amazonS3.getObjectMetadata(bucketName,fileName);
            FileManagement fileManagement = new FileManagement();
            fileManagement.setFileName(fileName);
            fileManagement.setFilePath(fileUrl);
            fileManagement.setSize(metadata.getContentLength());
            fileManagement.setFileType(fileName.substring(fileName.lastIndexOf(".")+1));
            fileManagement.setIsDelete(0);
            update(fileManagement);
        }catch (Exception e){
            throw new BussinessException(e.getMessage(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return fileUrl;
    }

    public String generateFileName(MultipartFile file){
        return new Date().getTime() + "_" + file.getOriginalFilename();
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(new Date().getTime() + "_" + multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    @Override
    public FileManagement create(FileManagement entity) {
        return repository.save(entity);
    }

    @Override
    public FileManagement retrieve(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void update(FileManagement entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        FileManagement fileManagement = retrieve(id);
        if(!ObjectUtils.isEmpty(fileManagement)){
            fileManagement.setIsDelete(1);
            update(fileManagement);
        }
    }
}
