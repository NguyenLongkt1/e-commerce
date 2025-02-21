package com.example.productservice.service;

import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductCommandService extends ICommandService<Product> {
    Product createProduct(@Valid ProductDTO dto);
    Product updateProduct(Long id,@Valid ProductDTO dto) throws BussinessException;
    Product doCreateOrUpdateProduct(ProductDTO dto, List<MultipartFile> files);
    ProductDTO retrieveById(Long id);
    List<Product> getAllProduct(String name, String code, Integer categoryId, Integer shopId);
}
