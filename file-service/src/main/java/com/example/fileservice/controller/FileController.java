package com.example.fileservice.controller;

import com.example.common.exception.BussinessException;
import com.example.fileservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSingleFile(@RequestPart("file") MultipartFile file) throws BussinessException {
        return ResponseEntity.ok(fileService.uploadSingleFile(file));
    }
}
