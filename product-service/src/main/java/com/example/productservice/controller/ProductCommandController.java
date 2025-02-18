package com.example.productservice.controller;

import com.example.common.exception.BussinessException;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/command/products")
@Slf4j
public class ProductCommandController{

    @Autowired
    ProductCommandService productCommandService;

    @Autowired
    ObjectMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> doRetrieve(@PathVariable("id") Long id){
        return ResponseEntity.ok(productCommandService.retrieveById(id));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO dto) throws BussinessException {
//        return ResponseEntity.ok(productCommandService.updateProduct(id,dto));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> doDelete(@PathVariable("id") Long id){
        productCommandService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String name, @RequestParam(required = false) String code) {
        return ResponseEntity.ok(productCommandService.getAllProduct(name, code));
    }

    @PostMapping
    public ResponseEntity<Product> createOrUpdateProduct(@RequestPart(value="data") String dto, @RequestPart(value = "files",required = false) List<MultipartFile> file) throws JsonProcessingException {
        ProductDTO userDto = mapper.readValue(dto,ProductDTO.class);
        return ResponseEntity.ok(productCommandService.doCreateOrUpdateProduct(userDto,file));
    }
}
