package com.sparta.shopapi.domain.cart.repository;

import com.sparta.shopapi.domain.cart.entity.Cart;
import com.sparta.shopapi.domain.cart.entity.CartItem;
import com.sparta.shopapi.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCart(Cart cart);

    CartItem findByProductAndCart(Product product, Cart cart);

    boolean existsByCartAndProduct(Cart cart, Product product);
}
