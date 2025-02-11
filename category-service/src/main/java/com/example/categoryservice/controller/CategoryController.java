package com.example.categoryservice.controller;

import com.example.categoryservice.dto.CategoryDTO;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.service.CategoryService;
import com.example.common.exception.BussinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ObjectMapper mapper;

//    @PostMapping
//    public ResponseEntity<Category> doCreate(@RequestBody CategoryDTO categoryDTO){
//        return ResponseEntity.ok(categoryService.doCreate(categoryDTO));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> retrieve(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.retrieve(id));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Category> doUpdate(@RequestBody CategoryDTO categoryDTO) throws BussinessException {
//        return ResponseEntity.ok(categoryService.doUpdate(categoryDTO));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> doDelete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.getAllCategory(name));
    }

    @PostMapping
    public ResponseEntity<Category> createOrUpdateCategory(@RequestPart(value="data") String dto, @RequestPart(value = "file",required = false) MultipartFile file) throws JsonProcessingException {
        CategoryDTO userDto = mapper.readValue(dto,CategoryDTO.class);
        return ResponseEntity.ok(categoryService.doCreateOrUpdateUser(userDto,file));
    }
}
