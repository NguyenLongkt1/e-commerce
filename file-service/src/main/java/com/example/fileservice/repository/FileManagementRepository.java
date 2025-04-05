package com.example.fileservice.repository;

import com.example.fileservice.entity.FileManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileManagementRepository extends JpaRepository<FileManagement,Long> {
    List<FileManagement> findByIdIn(List<Long> ids);
    @Query(value = "select * from file-management f " +
            "where f.is_delete = 0 and f.id = ?1 ",nativeQuery = true)
    FileManagement getFileById(Long fileId);
}
