package com.sparta.shopapi.domain.cart.controller;

import com.sparta.shopapi.domain.cart.service.CartService;
import com.sparta.shopapi.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> addCart(@AuthenticationPrincipal UserDetailsImpl member,
                                     @RequestParam(value = "prod") Long productId,
                                     @RequestParam(defaultValue = "1", value = "cnt") int count) {
        cartService.addCart(member.getUsername(), productId, count);
        return ResponseEntity.ok().body("상품을 장바구니에 담았습니다.");
    }

    @GetMapping
    public ResponseEntity<?> readCart(@AuthenticationPrincipal UserDetailsImpl member) {
        return ResponseEntity.ok(cartService.readCart(member.getUsername()));
    }

    @PutMapping
    public ResponseEntity<?> updateCount(@AuthenticationPrincipal UserDetailsImpl member,
                                        @RequestParam(value = "productId") Long productId,
                                        @RequestParam(value = "cnt") int count) {
        cartService.updateCount(member.getUsername(), productId, count);
        return ResponseEntity.ok("상품 수량을 수정하였습니다.");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal UserDetailsImpl member,
                                        @PathVariable Long productId) {
        cartService.deleteProduct(member.getUsername(), productId);
        return ResponseEntity.ok("선택하신 상품을 삭제하였습니다.");
    }

}
