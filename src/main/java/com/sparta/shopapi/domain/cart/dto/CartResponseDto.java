package com.sparta.shopapi.domain.cart.dto;

import com.sparta.shopapi.domain.cart.entity.CartItem;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CartResponseDto {

    @Getter
    @Builder
    @Schema(description = "장바구니 응답 정보")
    public static class Read {

        @ArraySchema(schema = @Schema(description = "장바구니에 담긴 상품 목록"))
        private List<ReadItem> readItems;

        @Schema(description = "장바구니에 담긴 상품들의 총 가격")
        private int totalPrice;

    }

    @Getter
    @Builder
    @Schema(description = "장바구니 응답 상세 정보")
    public static class ReadItem {

        @Schema(description = "상품 정보")
        private final ProductResponseDto.Read product;

        @Schema(description = "장바구니에 담긴 상품 수량")
        private final int count;

        @Schema(description = "Cart Item을 생성하는 메소드")
        public static ReadItem from(CartItem cartItem, ProductResponseDto.Read product) {
            return ReadItem.builder()
                    .product(product)
                    .count(cartItem.getCount())
                    .build();
        }
    }
}
