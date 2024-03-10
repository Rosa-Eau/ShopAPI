package com.sparta.shopapi.domain.product.service;

import com.sparta.shopapi.domain.product.dto.ProductRequestDto;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
import com.sparta.shopapi.domain.product.entity.Product;
import com.sparta.shopapi.domain.product.repository.ProductRepository;
import com.sparta.shopapi.global.handler.exception.BusinessException;
import com.sparta.shopapi.global.handler.exception.ErrorCode;
import com.sparta.shopapi.global.s3.S3UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;

@Service
@Slf4j
public class ProductService {

    private static final int PAGE_SIZE = 5;
    private final ProductRepository productRepository;
    private final S3UploadService service;

    public ProductService(ProductRepository productRepository, S3UploadService service) {
        this.productRepository = productRepository;
        this.service = service;
    }

    @Transactional
    public ProductResponseDto.Register registerProduct(ProductRequestDto requestDto, MultipartFile image) throws IOException {

        String imageUrl = service.saveFile(image);

        productRepository.save(requestDto.toEntity(imageUrl));

        return ProductResponseDto.Register.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .description(requestDto.getDescription())
                .category(requestDto.getCategory())
                .imageUrl(imageUrl)
                .build();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto.Read readProduct(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));

        return ProductResponseDto.Read.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto.Read> readProductList(int pageNo, String sort, boolean asc) {

        Pageable pageable = null;

        if (!asc) {
            pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, sort));
        }

        if (asc) {
            pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.ASC, sort));
        }

        Page<ProductResponseDto.Read> page = productRepository.findAll(pageable).map(ProductResponseDto.Read::from);

        return page.getContent();
    }
}
