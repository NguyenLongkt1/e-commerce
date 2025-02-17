package com.example.categoryservice.service;

import com.example.categoryservice.dto.CategoryDTO;
import com.example.categoryservice.entity.Category;
import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService extends ICommandService<Category> {
    Category doCreate(@Valid CategoryDTO categoryDTO);
    Category doUpdate(@Valid CategoryDTO categoryDTO) throws BussinessException, BussinessException;
    List<Category> getAllCategory(String name);
    Category doCreateOrUpdateCategory(CategoryDTO dto, MultipartFile file);
}
