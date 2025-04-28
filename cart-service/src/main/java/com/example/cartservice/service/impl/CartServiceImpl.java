package com.example.cartservice.service.impl;

import com.example.cartservice.dto.CartDTO;
import com.example.cartservice.dto.ProductDTO;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartProduct;
import com.example.cartservice.repository.CartProductRepository;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Value("${product-service.host}")
    private String productServiceUrl;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Cart create(Cart entity) {
        return cartRepository.save(entity);
    }

    @Override
    public Cart retrieve(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Cart entity) {
        cartRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }


    @Override
    public Cart createCart(CartDTO cartDTO) {
        Cart cartEntity = objectMapper.convertValue(cartDTO, Cart.class);
        return create(cartEntity);
    }

    @Override
    public void addProductToCart(CartDTO cartDTO) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cartDTO.getCartId());
        cartProduct.setProductId(cartDTO.getProductId());
        cartProductRepository.save(cartProduct);
    }

    @Override
    @Transactional
    public void updateQuantity(CartDTO cartDTO) {
        cartProductRepository.updateQuantity(cartDTO.getCartId(), cartDTO.getProductId(), cartDTO.getQuantity());
    }

    @Override
    @Transactional
    public void removeProductFromCart(CartDTO cartDTO) {
        cartProductRepository.deleteByCartIdAndProductId(cartDTO.getCartId(), cartDTO.getProductId());
    }

    @Override
    public List<ProductDTO> getAllProductsInCart(Long cartId) {
        //Get danh sách productId theo cartId
        List<CartProduct> cartProducts = cartProductRepository.findByCartId(cartId);
        if (CollectionUtils.isEmpty(cartProducts)) return Collections.emptyList();

        //Gọi api bên product truyền vào 1 list productId -> lấy sản phẩm
        List<ProductDTO> productDTOs = getProductsInfo(cartProducts.stream().map(CartProduct::getProductId).toList());

        //Set số lượng cho từng sản phẩm
        Map<Long, Integer> productQuantityMap = cartProducts.stream()
                .collect(Collectors.toMap(
                        CartProduct::getProductId,
                        CartProduct::getQuantity
                ));
        productDTOs.forEach(productDTO -> {
            Integer quantity = productQuantityMap.get(productDTO.getId());
            productDTO.setQuantity(quantity);
        });
        return productDTOs;
    }

    @Override
    public Integer getAmountOfProductsInCart(Long cartId) {
        return cartProductRepository.getAmountOfProductsInCart(cartId);
    }

    private List<ProductDTO> getProductsInfo(List<Long> productIds) {
        String url = UriComponentsBuilder.fromUriString(productServiceUrl + "/command/products/get-by-ids")
                .queryParam("ids", productIds)
                .toUriString();

        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
