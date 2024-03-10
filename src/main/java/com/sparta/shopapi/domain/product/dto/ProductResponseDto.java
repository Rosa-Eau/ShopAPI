package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductResponseDto {

    @Getter
    @Builder
    @Schema(description = "상품 등록 응답 정보")
    public static class Register {

        @Schema(description = "상품명")
        private final String name;

        @Schema(description = "상품 가격")
        private final int price;

        @Schema(description = "상품 소개")
        private final String description;

        @Schema(description = "상품 카테고리")
        private final String category;

    }

    @Getter
    @Builder
    @Schema(description = "상품 조회 응답 정보")
    public static class Read {

        @Schema(description = "상품명")
        private final String name;

        @Schema(description = "상품 가격")
        private final int price;

        @Schema(description = "상품 소개")
        private final String description;

        @Schema(description = "상품 카테고리")
        private final String category;

        @Schema(description = "Product 엔티티에서 ReadDto로 변환하는 메소드")
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
