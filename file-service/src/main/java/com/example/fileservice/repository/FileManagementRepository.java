package com.example.fileservice.repository;

import com.example.fileservice.entity.FileManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileManagementRepository extends JpaRepository<FileManagement,Long> {
    List<FileManagement> findByIdIn(List<Long> ids);
}
