package com.example.categoryservice.service.impl;

import com.example.categoryservice.dto.CategoryDTO;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.repository.CategoryRepository;
import com.example.categoryservice.service.CategoryService;
import com.example.common.exception.BussinessException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

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
            category.setDelete(true);
            update(category);
        }
    }

    @Override
    @Transactional
    public Category doCreate(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        category.setDelete(false);
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

    @Override
    public List<Category> getAllCategory(String name) {
        return categoryRepository.findAllByName("%"+name+"%");
    }

    @Override
    public Category doCreateOrUpdateCategory(CategoryDTO dto, MultipartFile file) {
        Category category = modelMapper.map(dto, Category.class);

        if (ObjectUtils.isEmpty(dto.getId())) {
            category.setDelete(false);

            if (!ObjectUtils.isEmpty(file)) {
                String path = doUploadFile(file);
                category.setThumbnail(path);
            }

            return create(category);
        } else {
            Category oldCategory = retrieve(dto.getId());
            if (ObjectUtils.isEmpty(oldCategory)) {
                throw new RuntimeException("Not found category with id: " + dto.getId());
            }
            oldCategory.setName(category.getName());
            oldCategory.setCode(category.getCode());
            oldCategory.setDescription(category.getDescription());
            if (!ObjectUtils.isEmpty(file)) {
                String path = doUploadFile(file);
                oldCategory.setThumbnail(path);
            }
            update(oldCategory);
            return oldCategory;
        }
    }

    String doUploadFile(MultipartFile file) {

        String url = "http://localhost:8084/storage/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(file.getResource(), headers));

        // Tạo request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return response.getBody();
    }
}
