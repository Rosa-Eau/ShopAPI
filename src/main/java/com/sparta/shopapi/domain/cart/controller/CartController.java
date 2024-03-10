package com.sparta.shopapi.domain.cart.controller;

import com.sparta.shopapi.domain.cart.service.CartService;
import com.sparta.shopapi.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/cart")
@Tag(name = "Cart", description = "장바구니에 상품을 추가, 조회, 수정, 삭제합니다.")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "장바구니에 상품 추가",
            description = "장바구니에 특정 상품을 지정된 수량만큼 추가합니다."
    )
    @PostMapping
    public ResponseEntity<?> addCart(
            @AuthenticationPrincipal UserDetailsImpl member,
            @Parameter(name = "prod", description = "추가할 상품의 ID")
            @RequestParam Long productId,
            @Parameter(name = "cnt", description = "추가할 상품의 수량", example = "1")
            @RequestParam(defaultValue = "1") int count) {
        cartService.addCart(member.getUsername(), productId, count);
        return ResponseEntity.ok().body("상품을 장바구니에 성공적으로 추가했습니다.");
    }

    @Operation(
            summary = "장바구니 내용 조회",
            description = "현재 로그인한 사용자의 장바구니 내용을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<?> readCart(
            @AuthenticationPrincipal UserDetailsImpl member) {
        return ResponseEntity.ok(cartService.readCart(member.getUsername()));
    }

    @Operation(
            summary = "상품 수량 수정",
            description = "장바구니 내 특정 상품의 수량을 수정합니다."
    )
    @PutMapping
    public ResponseEntity<?> updateCount(
            @AuthenticationPrincipal UserDetailsImpl member,
            @Parameter(name = "productId", description = "수정할 상품의 ID")
            @RequestParam Long productId,
            @Parameter(name = "cnt", description = "수정할 상품의 수량", example = "1")
            @RequestParam int count) {
        cartService.updateCount(member.getUsername(), productId, count);
        return ResponseEntity.ok("상품 수량이 성공적으로 수정되었습니다.");
    }

    @Operation(
            summary = "특정 상품을 삭제",
            description = "장바구니에서 특정 상품을 삭제합니다."
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteCart(
            @AuthenticationPrincipal UserDetailsImpl member,
            @Parameter(name = "productId", description = "삭제할 상품의 ID")
            @PathVariable Long productId) {
        cartService.deleteProduct(member.getUsername(), productId);
        return ResponseEntity.ok("선택한 상품이 장바구니에서 삭제되었습니다.");
    }

}
