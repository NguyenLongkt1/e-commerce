package com.example.productservice.entity;

import com.example.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "product_file")
@Entity
public class ProductFile extends BaseEntity {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "file_id")
    private Long fileId;
}
