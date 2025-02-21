package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCommandRepository extends JpaRepository<Product,Long> {
    @Query("""
           select a from Product a where a.isDelete = false
            and (:name is null or a.name like %:name%)
            and (:code is null or a.code like %:code%)
            and (:categoryId is null or a.categoryId = :categoryId)
            and (:shopId is null or a.shopId = :shopId)
            """)
    List<Product> findAllByName(String name, String code, Integer categoryId, Integer shopId);
}
