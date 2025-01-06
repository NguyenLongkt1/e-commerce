package com.example.categoryservice.controller;

import com.example.categoryservice.dto.CategoryDTO;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.exception.BussinessException;
import com.example.categoryservice.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> doCreate(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.doCreate(categoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> retrieve(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.retrieve(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> doUpdate(@RequestBody CategoryDTO categoryDTO) throws BussinessException {
        return ResponseEntity.ok(categoryService.doUpdate(categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> doDelete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.ok(true);
    }
}
