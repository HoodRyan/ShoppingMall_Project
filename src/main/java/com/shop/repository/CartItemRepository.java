package com.shop.repository;

import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cardId, Long itemId); // 카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회
}