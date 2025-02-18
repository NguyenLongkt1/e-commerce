package com.example.fileservice.controller;

import com.example.common.exception.BussinessException;
import com.example.fileservice.entity.FileManagement;
import com.example.fileservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/get-files-by-ids")
    public ResponseEntity<List<FileManagement>> getFilesByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(fileService.getFilesByIds(ids));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSingleFile(@RequestPart("file") MultipartFile file) throws BussinessException {
        return ResponseEntity.ok(fileService.uploadSingleFile(file));
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<List<Long>> uploadMultipleFile(@RequestPart("files") List<MultipartFile> files) throws BussinessException {
        return ResponseEntity.ok(fileService.uploadMultipleFiles(files));
    }

    @DeleteMapping("/delete-files-by-ids")
    public ResponseEntity<List<FileManagement>> deleteFilesByIds(@RequestParam("ids") List<Long> ids) {
        fileService.deleteByIds(ids);
        return ResponseEntity.ok().build();
    }
}
