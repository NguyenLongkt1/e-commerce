package com.example.fileservice.service;

import com.example.common.exception.BussinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
     String uploadSingleFile(MultipartFile multipartFile) throws BussinessException;
}
