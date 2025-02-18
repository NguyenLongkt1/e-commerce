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

    public ProductFile() {
    }

    public ProductFile(Long productId, Long fileId) {
        this.productId = productId;
        this.fileId = fileId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
