package com.example.productservice.service;

import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import jakarta.validation.Valid;

public interface ProductCommandService extends ICommandService<Product> {
    Product createProduct(@Valid ProductDTO dto);
    Product updateProduct(Long id,@Valid ProductDTO dto) throws BussinessException;
}
