package com.example.fileservice.repository;

import com.example.fileservice.entity.FileManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileManagementRepository extends JpaRepository<FileManagement,Long> {
}
