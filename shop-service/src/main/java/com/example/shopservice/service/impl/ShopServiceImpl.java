package com.example.shopservice.service.impl;

import com.example.common.exception.BussinessException;
import com.example.shopservice.dto.ShopDTO;
import com.example.shopservice.entity.Shop;
import com.example.shopservice.repository.ShopRepository;
import com.example.shopservice.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Shop create(Shop entity) {
        return repository.save(entity);
    }

    @Override
    public Shop retrieve(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void update(Shop entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Shop shop = retrieve(id);
        if(!ObjectUtils.isEmpty(shop)){
            shop.setDelete(true);
            update(shop);
        }
    }

    @Override
    public Shop createOrUpdate(ShopDTO dto, MultipartFile file) throws BussinessException {
        Shop shop = modelMapper.map(dto,Shop.class);
        if(!ObjectUtils.isEmpty(file)){
            String avatar = doUploadFile(file);
            shop.setAvatar(avatar);
        }
        if(ObjectUtils.isEmpty(shop.getId())){
            shop.setDelete(false);
            if(ObjectUtils.isEmpty(shop.getRating())){
                shop.setRating(0d);
            }
            if(ObjectUtils.isEmpty(shop.getFollower())){
                shop.setFollower(0);
            }
            create(shop);
            return shop;
        }else{
            Shop oldInfo = retrieve(shop.getId());
            if(ObjectUtils.isEmpty(oldInfo)){
                throw new BussinessException("Not found shop with id: "+shop.getId(),HttpStatus.NOT_FOUND.value());
            }
            oldInfo.setShopName(shop.getShopName());
            oldInfo.setAddress(shop.getAddress());
            oldInfo.setDescription(shop.getDescription());
            oldInfo.setStatus(shop.getStatus());
            oldInfo.setAvatar(shop.getAvatar());
            update(oldInfo);
            return oldInfo;
        }
    }

    @Override
    public Page<Shop> getAllByIsDeleteAndStatus(String shopName, Integer status, Pageable pageable) {
        if(!ObjectUtils.isEmpty(shopName)){
            shopName = shopName.trim().toLowerCase();
        }
        return repository.getPageShop(shopName,status,pageable);
    }

    String doUploadFile(MultipartFile file) {

        String url = "http://localhost:8084/storage/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(file.getResource(), headers));

        // Tạo request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return response.getBody();
    }
}
