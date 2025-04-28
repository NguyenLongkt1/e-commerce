package com.example.productservice.controller;

import com.example.common.exception.BussinessException;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<ProductDTO>> getAllProduct(@RequestParam(required = false) String name, @RequestParam(required = false) String code,
                                                       @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer shopId) {
        return ResponseEntity.ok(productCommandService.getAllProduct(name, code, categoryId, shopId));
    }

    @PostMapping
    public ResponseEntity<Product> createOrUpdateProduct(@RequestPart(value="data") String dto, @RequestPart(value = "files",required = false) List<MultipartFile> file) throws JsonProcessingException {
        ProductDTO userDto = mapper.readValue(dto,ProductDTO.class);
        return ResponseEntity.ok(productCommandService.doCreateOrUpdateProduct(userDto,file));
    }

    @GetMapping("/get-by-shop/{id}")
    public ResponseEntity<Page<ProductDTO>> getAllProduct(@PathVariable("id") Long shopId,
                                                          @RequestParam(name="pageIndex",defaultValue = "1",required = false) Integer pageIndex,
                                                          @RequestParam(name="pageSize",defaultValue = "10",required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return ResponseEntity.ok(productCommandService.getProductsByShopId(shopId,pageable));
    }

    @GetMapping("/get-by-ids")
    public ResponseEntity<List<ProductDTO>> getProductByIds(@RequestParam(name="ids") List<Long> ids) {
        return ResponseEntity.ok(productCommandService.getProductByIds(ids));
    }
}
