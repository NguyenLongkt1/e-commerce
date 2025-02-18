package com.example.productservice.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {
    private Long id;

    @NotNull(message = "Tên sản phẩm không được để trống")
    @Max(value = 500, message = "Tên sản phẩm không được vượt quá 500 kí tự")
    private String name;

    @NotNull(message = "Mã sản phẩm không được để trống")
    @Max(value = 100, message = "Mã sản phẩm không được vượt quá 100 kí tự")
    private String code;
    private String description;
    private Integer rating;
    @NotNull(message = "Giá sản phẩm không được để trống")
    private Double price;
    private Long categoryId;
    private Long createdBy;
    private LocalDateTime createdDate;
    private Boolean isDelete;
    private List<Long> lstRemovedFileId;
    private List<FileDTO> lstFile;
    private Long shopId;

    public ProductDTO() {
    }

    public ProductDTO(String name, String code, String description, Integer rating, Double price, Long categoryId, List<Long> lstRemovedFileId,
                      Long createdBy, LocalDateTime createdDate, Boolean isDelete, List<FileDTO> lstFile, Long shopId) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.categoryId = categoryId;
        this.lstRemovedFileId = lstRemovedFileId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.isDelete = isDelete;
        this.lstFile = lstFile;
        this.shopId = shopId;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getLstRemovedFileId() {
        return lstRemovedFileId;
    }

    public void setLstRemovedFileId(List<Long> lstRemovedFileId) {
        this.lstRemovedFileId = lstRemovedFileId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public List<FileDTO> getLstFile() {
        return lstFile;
    }

    public void setLstFile(List<FileDTO> lstFile) {
        this.lstFile = lstFile;
    }
}
