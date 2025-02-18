package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import com.example.productservice.entity.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductFileRepository extends JpaRepository<ProductFile,Long> {
    @Query("select a.fileId from ProductFile a where a.productId = :productId")
    List<Long> findFileIdsByProductId(Long productId);

    void deleteByProductIdAndFileIdIn(Long productId, List<Long> fileIds);
}
