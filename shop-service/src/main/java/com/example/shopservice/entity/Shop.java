package com.example.shopservice.entity;

import com.example.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "shop")
@Entity
@NoArgsConstructor
public class Shop extends BaseEntity {

    @Column(name = "shop_name", columnDefinition = "VARCHAR(255)")
    private String shopName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "status", columnDefinition = "INT(1)")
    private Integer status;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "follower", columnDefinition = "INT")
    private Integer follower;

    @Column(name = "is_delete")
    private Boolean isDelete;

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
}
