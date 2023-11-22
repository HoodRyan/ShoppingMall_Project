package com.shop.entity;

import com.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;     // 하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로 주문 상품 기준으로 다대일 단방향 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    // 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑

    private int orderPrice; // 주문 가격

    private int count;  // 수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문할 상품과 주문 수량 세팅
        orderItem.setCount(count); // 주문할 상품과 주문 수량 세팅
        orderItem.setOrderPrice(item.getPrice()); // 현재 시간 기준으로 상품 가격을 주문 가격으로 설정. 상품가격은 시간에 따라 바뀔 수 있음.

        item.removeStock(count);    // 주문 수량만큼 상품의 재고 수량을 감소
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;    // 주문 가격과 주문 수량을 곱해서 해당 상품을 주문한 총 가격 계산 메소드
    }


//    private LocalDateTime regTime;    // 삭제

//    private LocalDateTime updateTime; // 삭제
}
