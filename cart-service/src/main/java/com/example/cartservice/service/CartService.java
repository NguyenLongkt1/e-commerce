package com.example.cartservice.service;

import com.example.cartservice.dto.CartDTO;
import com.example.cartservice.dto.ProductDTO;
import com.example.cartservice.entity.Cart;
import com.example.common.service.ICommandService;

import java.util.List;

public interface CartService extends ICommandService<Cart> {
    Cart createCart(CartDTO cartDTO);
    void addProductToCart(CartDTO cartDTO);
    void updateQuantity(CartDTO cartDTO);
    void removeProductFromCart(CartDTO cartDTO);
    List<ProductDTO> getAllProductsInCart(Long cartId);
    Integer getAmountOfProductsInCart(Long cartId);
    CartDTO getCartByUserId(Long userId);
}
