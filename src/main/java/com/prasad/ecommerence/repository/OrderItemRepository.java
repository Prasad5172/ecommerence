package com.prasad.ecommerence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prasad.ecommerence.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
