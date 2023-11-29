package com.prasad.ecommerence.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.ecommerence.model.OrderItem;

public interface OrderItemService  {
    public OrderItem createOrderItem(OrderItem orderItem);
}
