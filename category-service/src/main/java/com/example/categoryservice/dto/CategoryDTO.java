package com.example.categoryservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private Long id;

    @NotNull(message = "Tên danh mục không được để trống")
    @Max(value = 255, message = "Tên danh mục không được vượt quá 255 kí tự")
    private String name;

    @NotNull(message = "Mã danh mục không được để trống")
    @Max(value = 50, message = "Mã danh mục không được vượt quá 50 kí tự")
    private String code;
    private String description;
    private Long createdBy;
    private Boolean isDelete;

    public CategoryDTO(){}

    public CategoryDTO(Long id, String name, String code, String description, Long createdBy, Boolean isDelete) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.createdBy = createdBy;
        this.isDelete = isDelete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
