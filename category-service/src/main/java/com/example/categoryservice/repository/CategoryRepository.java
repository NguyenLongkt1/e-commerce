package com.example.categoryservice.repository;

import com.example.categoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select a from Category a where a.isDelete = false and (:name is null or a.name like :name)")
    List<Category> findAllByName(String name);
}
