package com.example.categoryservice.service;

import com.example.categoryservice.dto.CategoryDTO;
import com.example.categoryservice.entity.Category;
import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import jakarta.validation.Valid;

public interface CategoryService extends ICommandService<Category> {
    Category doCreate(@Valid CategoryDTO categoryDTO);
    Category doUpdate(@Valid CategoryDTO categoryDTO) throws BussinessException, BussinessException;
}
