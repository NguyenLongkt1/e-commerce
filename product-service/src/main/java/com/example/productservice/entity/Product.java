package com.example.productservice.entity;

import com.example.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "products")
@Entity
public class Product extends BaseEntity {
    @Column(name = "name", columnDefinition = "VARCHAR(500)")
    private String name;

    @Column(name = "code", columnDefinition = "VARCHAR(100)")
    private String code;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "price")
    private Double price;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "shop_id")
    private Long shopId;

    public Product() {
    }

    public Product(String name, String code, String description, Integer rating, Double price,
                   Long categoryId, Boolean isDelete, Long shopId) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.categoryId = categoryId;
        this.isDelete = isDelete;
        this.shopId = shopId;
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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
