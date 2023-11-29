package com.prasad.ecommerence.service;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.model.OrderItem;
import com.prasad.ecommerence.repository.OrderItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
