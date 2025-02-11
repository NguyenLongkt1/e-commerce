package com.example.shopservice.controller;

import com.example.common.exception.BussinessException;
import com.example.shopservice.dto.ShopDTO;
import com.example.shopservice.entity.Shop;
import com.example.shopservice.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/shops")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    ObjectMapper mapper;

    @PostMapping
    public ResponseEntity<Shop> createOrUpdateShop(@RequestPart(value="data") String dto, @RequestPart(value = "file",required = false) MultipartFile file) throws JsonProcessingException, BussinessException {
        ShopDTO shopDTO = mapper.readValue(dto,ShopDTO.class);
        return ResponseEntity.ok(shopService.createOrUpdate(shopDTO,file));
    }

    @GetMapping
    public ResponseEntity<Page<Shop>> doSearchShop(@RequestParam(value = "shopName",required = false) String shopName, @RequestParam(value = "status",required = false) Integer status,
                                                   @RequestParam(value = "pageIndex",defaultValue = "0") Integer pageIndex, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        return ResponseEntity.ok(shopService.getAllByIsDeleteAndStatus(shopName,status,pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> findShopById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shopService.retrieve(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteShop(@PathVariable("id") Long id){
        shopService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
