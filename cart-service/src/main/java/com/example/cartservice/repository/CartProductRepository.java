package com.example.cartservice.repository;

import com.example.cartservice.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    @Query("select a from CartProduct a where a.cartId = :cartId")
    List<CartProduct> findByCartId(Long cartId);

    @Query("select count(a.productId) from CartProduct a where a.cartId = :cartId")
    Integer getAmountOfProductsInCart(Long cartId);

    @Modifying
    @Query("update CartProduct a set a.quantity = :quantity where a.cartId = :cartId and a.productId = :productId")
    void updateQuantity(Long cartId, Long productId, Integer quantity);

    @Modifying
    @Query("delete from CartProduct a where a.cartId = :cartId and a.productId = :productId")
    void deleteByCartIdAndProductId(Long cartId, Long productId);
}
