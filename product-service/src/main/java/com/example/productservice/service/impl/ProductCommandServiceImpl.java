package com.example.productservice.service.impl;

import com.example.common.exception.BussinessException;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductCommandRepository;
import com.example.productservice.service.ProductCommandService;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    @Autowired
    ProductCommandRepository productCommandRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public Product create(Product entity) {
        return productCommandRepository.save(entity);
    }

    @Override
    public Product retrieve(Long id) {
        return productCommandRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Product entity) {
        productCommandRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Product product = retrieve(id);
        if(!ObjectUtils.isEmpty(product)){
            product.setDelete(true);
            update(product);
        }
    }

    @Override
    public Product createProduct(ProductDTO dto) {
        Product product = modelMapper.map(dto,Product.class);
        return create(product);
    }

    @Override
    public Product updateProduct(Long id,ProductDTO dto) throws BussinessException {
        if(ObjectUtils.isEmpty(id)){
            throw new BussinessException("Not found product with id "+id, HttpStatus.SC_NOT_FOUND);
        }
        Product product = retrieve(id);
        if(!ObjectUtils.isEmpty(product)){
            product.setCode(dto.getCode().trim());
            product.setName(dto.getName().trim());
            product.setDescription(dto.getDescription().trim());
            product.setPrice(dto.getPrice());
            product.setCategoryId(dto.getCategoryId());
            if(!ObjectUtils.isEmpty(dto.getDelete())){
                product.setDelete(dto.getDelete());
            }
            if(!ObjectUtils.isEmpty(product.getRating())){
                product.setRating(product.getRating());
            }
            update(product);
            return product;
        }
        return null;
    }
}
