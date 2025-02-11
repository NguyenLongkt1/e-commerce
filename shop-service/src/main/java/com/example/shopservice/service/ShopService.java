package com.example.shopservice.service;

import com.example.common.exception.BussinessException;
import com.example.common.service.ICommandService;
import com.example.shopservice.dto.ShopDTO;
import com.example.shopservice.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopService extends ICommandService<Shop> {

    Shop createOrUpdate(ShopDTO dto, MultipartFile file) throws BussinessException;
    Page<Shop> getAllByIsDeleteAndStatus(String shopName, Integer status, Pageable pageable);

}
