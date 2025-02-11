package com.example.fileservice.service;

import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import com.example.fileservice.entity.FileManagement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService extends ICommandService<FileManagement> {
     String uploadSingleFile(MultipartFile multipartFile) throws BussinessException;
}
