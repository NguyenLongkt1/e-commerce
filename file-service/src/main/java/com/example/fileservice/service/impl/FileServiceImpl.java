package com.example.fileservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.example.common.exception.BussinessException;
import com.example.fileservice.service.FileService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    @Override
    public String uploadSingleFile(MultipartFile multipartFile) throws BussinessException {
        String fileUrl;
        try {
            String fileName = generateFileName(multipartFile);
            File convertFile = convertMultipartFileToFile(multipartFile);
            amazonS3.putObject(bucketName, fileName, convertFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
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
}
