package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNm(String itemNm); // 쿼리 메소드를 이용한 상품 조회하기

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // OR 조건 처리하기

    List<Item> findByPriceLessThan(Integer price);  // LessThan 조건 처리하기

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price); // OrderBy로 정렬 처리하기

}
