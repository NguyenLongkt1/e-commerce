package com.example.fileservice.service;

import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import com.example.fileservice.entity.FileManagement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService extends ICommandService<FileManagement> {
     String uploadSingleFile(MultipartFile multipartFile) throws BussinessException;
     List<Long> uploadMultipleFiles(List<MultipartFile> multipartFiles) throws BussinessException;
     List<FileManagement> getFilesByIds(List<Long> ids);
     void deleteByIds(List<Long> ids);
}
