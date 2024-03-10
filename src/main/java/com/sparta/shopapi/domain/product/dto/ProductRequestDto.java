package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Schema(description = "상품 등록 요청 정보")
public class ProductRequestDto {

        @NotBlank(message = "상품명을 입력하세요.")
        @Schema(description = "상품명")
        private String name;

        @Positive(message = "가격을 입력하세요.")
        @Schema(description = "상품 가격", minimum = "0")
        private int price;

        @NotBlank(message = "소개를 입력하세요.")
        @Schema(description = "상품 소개")
        private String description;

        @NotBlank(message = "카테고리를 입력하세요.")
        @Schema(description = "상품 카테고리")
        private String category;

        @Schema(hidden = true)
        public Product toEntity(String imageUrl) {
            return Product.builder()
                    .name(name)
                    .imageUrl(imageUrl)
                    .price(price)
                    .description(description)
                    .category(category)
                    .build();
        }
}
