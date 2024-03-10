package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import com.sparta.shopapi.domain.product.service.ProductService;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductResponseDto {

    @Getter
    @Builder
    public static class Register {

        private final String name;

        private final int price;

        private final String description;

        private final String category;

    }

    @Getter
    @Builder
    public static class Read {
        private final String name;

        private final int price;

        private final String description;

        private final String category;

        public static Read from(Product product) {
            return Read.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .category(product.getCategory())
                    .build();
        }
    }

}
