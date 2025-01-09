package com.example.productservice.controller;

import com.example.common.exception.BussinessException;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command/products")
@Slf4j
public class ProductCommandController{

    @Autowired
    ProductCommandService productCommandService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO dto){
        return ResponseEntity.ok(productCommandService.createProduct(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> doRetrieve(@PathVariable("id") Long id){
        return ResponseEntity.ok(productCommandService.retrieve(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO dto) throws BussinessException {
        return ResponseEntity.ok(productCommandService.updateProduct(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> doDelete(@PathVariable("id") Long id){
        productCommandService.delete(id);
        return ResponseEntity.ok().build();
    }
}
