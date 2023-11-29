package com.prasad.ecommerence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prasad.ecommerence.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
    
    @Query("select o from Order o where o.user.id= :userId and( o.orderStatus = 'PLACED' or o.orderStatus = 'CONFIRMED' or o.orderStatus = 'SHIPPED' or o.orderStatus = 'DELIVERED')")
    public List<Order> getAllUserOrders(@Param("userId") Long userId);
}
