package com.example.productservice.service.impl;

import com.example.common.exception.BussinessException;
import com.example.productservice.dto.FileDTO;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.entity.ProductFile;
import com.example.productservice.repository.ProductCommandRepository;
import com.example.productservice.repository.ProductFileRepository;
import com.example.productservice.service.ProductCommandService;
import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    @Autowired
    ProductCommandRepository productCommandRepository;

    @Autowired
    ProductFileRepository productFileRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Product create(Product entity) {
        return productCommandRepository.save(entity);
    }

    @Override
    public Product retrieve(Long id) {
        return productCommandRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDTO> getAllProduct(String name,String code, Integer categoryId, Integer shopId) {
        List<ProductDTO> result = new ArrayList<>();
        List<Product> lstProduct = productCommandRepository.findAllByName(name, code, categoryId, shopId);
        if(lstProduct != null){
            lstProduct.forEach((e)->{
                List<FileDTO> filePaths = getFilesByIds(productFileRepository.findFileIdsByProductId(e.getId()));
                ProductDTO dto = modelMapper.map(e,ProductDTO.class);
                dto.setLstFile(filePaths);
                result.add(dto);
            });
        }
        return result;
    }

    @Override
    public ProductDTO retrieveById(Long id) {
        Product productEntity = retrieve(id);

        ProductDTO productDTO = new ProductDTO();
        modelMapper.map(productEntity, productDTO);

        List<FileDTO> filePaths = getFilesByIds(productFileRepository.findFileIdsByProductId(id));
        productDTO.setLstFile(filePaths);
        return productDTO;
    }

    @Override
    public void update(Product entity) {
        productCommandRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Product product = retrieve(id);
        if(!ObjectUtils.isEmpty(product)){
            product.setDelete(true);
            update(product);
        }
    }

    @Override
    public Product createProduct(ProductDTO dto) {
        Product product = modelMapper.map(dto,Product.class);
        return create(product);
    }

    @Override
    public Product updateProduct(Long id,ProductDTO dto) throws BussinessException {
        if(ObjectUtils.isEmpty(id)){
            throw new BussinessException("Not found product with id "+id, HttpStatus.SC_NOT_FOUND);
        }
        Product product = retrieve(id);
        if(!ObjectUtils.isEmpty(product)){
            product.setCode(dto.getCode().trim());
            product.setName(dto.getName().trim());
            product.setDescription(dto.getDescription().trim());
            product.setPrice(dto.getPrice());
            product.setCategoryId(dto.getCategoryId());
            if(!ObjectUtils.isEmpty(dto.getDelete())){
                product.setDelete(dto.getDelete());
            }
            if(!ObjectUtils.isEmpty(product.getRating())){
                product.setRating(product.getRating());
            }
            update(product);
            return product;
        }
        return null;
    }

    @Override
    @Transactional
    public Product doCreateOrUpdateProduct(ProductDTO dto, List<MultipartFile> files) {
        Product product = modelMapper.map(dto, Product.class);

        if (ObjectUtils.isEmpty(dto.getId())) {
            product.setDelete(false);
            if(dto.getRating() == null){
                product.setRating(5.0);
            }
            create(product);

            if (!CollectionUtils.isEmpty(files)) {
                List<Long> fileIds = doUploadFiles(files);
                List<ProductFile> productFiles = new ArrayList<>(fileIds.stream().map(fileId -> new ProductFile(product.getId(), fileId)).toList());
                productFileRepository.saveAll(productFiles);
            }

            return product;
        } else {
            Product oldProduct = retrieve(dto.getId());
            if (ObjectUtils.isEmpty(oldProduct)) {
                throw new RuntimeException("Not found category with id: " + dto.getId());
            }
            oldProduct.setName(product.getName());
            oldProduct.setCode(product.getCode());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setCategoryId(product.getCategoryId());
            oldProduct.setCategoryName(product.getCategoryName());
            oldProduct.setShopId(product.getShopId());
            oldProduct.setShopName(product.getShopName());
            update(oldProduct);

            //Xóa files
            if (!CollectionUtils.isEmpty(dto.getLstRemovedFileId())) {
                productFileRepository.deleteByProductIdAndFileIdIn(oldProduct.getId(), dto.getLstRemovedFileId());
                deleteFilesByIds(dto.getLstRemovedFileId());
            }

            //Thêm mới files
            if (!CollectionUtils.isEmpty(files)) {
                List<Long> fileIds = doUploadFiles(files);
                List<ProductFile> productFiles = new ArrayList<>(fileIds.stream().map(fileId -> new ProductFile(oldProduct.getId(), fileId)).toList());
                productFileRepository.saveAll(productFiles);
            }
            return oldProduct;
        }
    }


    private List<Long> doUploadFiles(List<MultipartFile> files) {

        String url = "http://localhost:8084/storage/upload-multiple";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        for (MultipartFile file : files) {
            body.add("files", new HttpEntity<>(file.getResource(), headers));
        }

        // Tạo request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<List<Long>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    private List<FileDTO> getFilesByIds(List<Long> fileIds) {

        String url = UriComponentsBuilder.fromUriString("http://localhost:8084/storage/get-files-by-ids")
                .queryParam("ids", fileIds)
                .toUriString();

        // Tạo request entity
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<FileDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    private void deleteFilesByIds(List<Long> fileIds) {

        String url = UriComponentsBuilder.fromUriString("http://localhost:8084/storage/delete-files-by-ids")
                .queryParam("ids", fileIds)
                .toUriString();

        // Tạo request entity
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
    }
}
