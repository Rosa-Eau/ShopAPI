package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductRequestDto {


    @Getter
    public static class Register {
        @NotBlank(message = "상품명을 입력하세요.")
        String name;

        @Positive(message = "가격을 입력하세요.")
        int price;

        @NotBlank(message = "소개를 입력하세요.")
        String description;

        @NotBlank(message = "카테고리를 입력하세요.")
        String category;

        public Product toEntity() {
            return Product.builder()
                    .name(name)
                    .price(price)
                    .description(description)
                    .category(category)
                    .build();
        }
    }

}
