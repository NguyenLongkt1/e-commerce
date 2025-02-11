package com.example.shopservice.dto;

import com.example.shopservice.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class ShopDTO {
    private Long id;
    private String shopName;
    private String address;
    private Integer status;
    private Double rating;
    private String avatar;
    private String description;
    private Integer follower;
    private Boolean isDelete;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private Long createdBy;

    public ShopDTO() {
    }

    public ShopDTO(Long id, String shopName, String address, Integer status, Double rating, String avatar, String description, Integer follower, Boolean isDelete, LocalDateTime createdDate, Long createdBy) {
        this.id = id;
        this.shopName = shopName;
        this.address = address;
        this.status = status;
        this.rating = rating;
        this.avatar = avatar;
        this.description = description;
        this.follower = follower;
        this.isDelete = isDelete;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
