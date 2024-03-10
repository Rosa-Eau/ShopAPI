package com.sparta.shopapi.domain.product.controller;

import com.sparta.shopapi.domain.product.dto.ProductRequestDto;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
import com.sparta.shopapi.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@Tag(name = "product", description = "상품 등록, 조회 API")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    @Operation(
            summary = "상품 등록",
            description = "관리자 권한이 필요한 API로 상품을 등록합니다.",
            tags = {"product"}
    )
    @SecurityRequirement(name = "bearerAuth")
    public ProductResponseDto.Register registerProduct(@RequestBody @Valid ProductRequestDto.Register requestDto){
        return productService.registerProduct(requestDto);
    }

    @GetMapping("/{productId}")
    @Operation(
            summary = "상품 조회",
            description = "상품 ID를 기반으로 상품을 조회하는 API입니다.",
            tags = {"product"}
    )
    public ProductResponseDto.Read readProduct(@PathVariable Long productId) {
        return productService.readProduct(productId);
    }

    @GetMapping
    @Operation(
            summary = "상품 목록 조회",
            description = "페이지 및 정렬 조건을 기반으로 상품 목록을 조회하는 API입니다.",
            tags = {"product"}
    )
    public List<ProductResponseDto.Read> readProductList(@Parameter(description = "페이지 번호") @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                         @Parameter(description = "정렬 기준") @RequestParam(required = false, defaultValue = "createdAt", value = "sort") String sort,
                                                         @Parameter(description = "내림차순/오름차순") @RequestParam(required = false, defaultValue = "true", value = "asc") boolean asc) {
        return productService.readProductList(pageNo, sort, asc);
    }



}
