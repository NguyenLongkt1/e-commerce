package com.example.shopservice.repository;

import com.example.shopservice.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    @Query(value = "select s.* from shop s " +
            "where (?1 is null or lower(s.shop_name) like %?1%) " +
            "and (?2 is null or s.status = ?2) " +
            "and s.is_delete = 0",nativeQuery = true)
    Page<Shop> getPageShop(String shopName, Integer status, Pageable pageable);
}
