package com.sparta.shopapi.domain.cart.service;

import com.sparta.shopapi.domain.cart.dto.CartResponseDto;
import com.sparta.shopapi.domain.cart.entity.Cart;
import com.sparta.shopapi.domain.cart.entity.CartItem;
import com.sparta.shopapi.domain.cart.repository.CartItemRepository;
import com.sparta.shopapi.domain.cart.repository.CartRepository;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.member.repository.MemberRepository;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
import com.sparta.shopapi.domain.product.entity.Product;
import com.sparta.shopapi.domain.product.repository.ProductRepository;
import com.sparta.shopapi.global.handler.exception.BusinessException;
import com.sparta.shopapi.global.handler.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void addCart(String username, Long productId, int count) {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new BusinessException(ErrorCode.ACCESS_DENIED_MEMBER));

        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));

        // find or create Member's Cart
        Cart cart = cartRepository.findByMember(member).orElseGet(() -> {
            return cartRepository.save(Cart.builder().member(member).build());
        });

        // 같은 상품 한 번 더 추가시 수량을 늘리고 하나를 더 추가하지 않도록 함
        if (cartItemRepository.existsByCartAndProduct(cart, product)) {
            CartItem cartItem = cartItemRepository.findByProductAndCart(product, cart);
            int totalCount = cartItem.getCount() + count;
            cartItem.update(totalCount);
            return;
        }

        cartItemRepository.save(CartItem.builder().cart(cart).product(product).count(count).build());
    }


    @Transactional(readOnly = true)
    public CartResponseDto.Read readCart(String username) {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new BusinessException(ErrorCode.ACCESS_DENIED_MEMBER));

        Cart cart = cartRepository.findByMember(member).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CART));

        List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);

        List<CartResponseDto.ReadItem> readItems = new ArrayList<>();

        int totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            readItems.add(CartResponseDto.ReadItem.from(cartItem, ProductResponseDto.Read.from(cartItem.getProduct())));
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getCount();
        }

        return CartResponseDto.Read.builder().readItems(readItems).totalPrice(totalPrice).build();
    }

    @Transactional
    public void updateCount(String username, Long productId, int count) {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new BusinessException(ErrorCode.ACCESS_DENIED_MEMBER));

        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));

        Cart cart = cartRepository.findByMember(member).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CART));

        CartItem cartItem = cartItemRepository.findByProductAndCart(product, cart);

        cartItem.update(count);
    }

    @Transactional
    public void deleteProduct(String username, Long productId) {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new BusinessException(ErrorCode.ACCESS_DENIED_MEMBER));

        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PRODUCT));

        Cart cart = cartRepository.findByMember(member).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CART));

        Long cartItemId = cartItemRepository.findByProductAndCart(product, cart).getId();

        cartItemRepository.deleteById(cartItemId);
    }
}
