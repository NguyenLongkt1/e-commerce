package com.example.cartservice.controller;

import com.example.cartservice.dto.CartDTO;
import com.example.cartservice.dto.ProductDTO;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@Slf4j
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/get-amount-of-product-in-cart")
    public ResponseEntity<Integer> getAmountOfProductsInCart(@RequestParam Long cartId) {
        return ResponseEntity.ok(cartService.getAmountOfProductsInCart(cartId));
    }

    @GetMapping("/get-products-in-cart")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCart(@RequestParam Long cartId) {
        return ResponseEntity.ok(cartService.getAllProductsInCart(cartId));
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.createCart(cartDTO));
    }

    @PostMapping("/add-product-to-cart")
    public ResponseEntity<Void> addProductToCart(@RequestBody CartDTO cartDTO) {
        cartService.addProductToCart(cartDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<Void> updateQuantity(@RequestBody CartDTO cartDTO) {
        cartService.updateQuantity(cartDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove-product-from-cart")
    public ResponseEntity<Void> removeProductFromCart(@RequestBody CartDTO cartDTO) {
        cartService.removeProductFromCart(cartDTO);
        return ResponseEntity.ok().build();
    }

}
