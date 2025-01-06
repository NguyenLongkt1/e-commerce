package com.example.categoryservice.service.impl;

import com.example.categoryservice.dto.CategoryDTO;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.exception.BussinessException;
import com.example.categoryservice.repository.CategoryRepository;
import com.example.categoryservice.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public Category create(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Category retrieve(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Category entity) {
        categoryRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Category category = retrieve(id);
        if(!ObjectUtils.isEmpty(category)){
            category.setIsDelete(true);
            update(category);
        }
    }

    @Override
    @Transactional
    public Category doCreate(CategoryDTO categoryDTO) {
        Category category = mapper.map(categoryDTO,Category.class);
        category.setIsDelete(false);
        return create(category);
    }

    @Override
    @Transactional
    public Category doUpdate(CategoryDTO categoryDTO) throws BussinessException {
        Category oldCategory = retrieve(categoryDTO.getId());
        if(ObjectUtils.isEmpty(oldCategory)){
            throw new BussinessException("Not found category with id: "+categoryDTO.getId(), HttpStatus.NOT_FOUND.value());
        }
        oldCategory.setCode(categoryDTO.getCode().trim());
        oldCategory.setName(categoryDTO.getName().trim());
        oldCategory.setDescription(categoryDTO.getDescription().trim());
        update(oldCategory);
        return oldCategory;
    }
}
