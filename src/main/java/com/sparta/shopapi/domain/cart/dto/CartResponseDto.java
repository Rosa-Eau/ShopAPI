package com.sparta.shopapi.domain.cart.dto;

import com.sparta.shopapi.domain.cart.entity.CartItem;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
import com.sparta.shopapi.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CartResponseDto {

    @Getter
    @Builder
    public static class Read {

        private List<ReadItem> readItems;
        private int totalPrice;

    }

    @Getter
    @Builder
    public static class ReadItem {

        private final ProductResponseDto.Read product;
        private final int count;

        public static ReadItem from(CartItem cartItem, ProductResponseDto.Read product) {
            return ReadItem.builder()
                    .product(product)
                    .count(cartItem.getCount())
                    .build();
        }
    }
}
