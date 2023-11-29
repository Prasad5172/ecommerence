package com.prasad.ecommerence.service;

import java.util.List;

import com.prasad.ecommerence.exception.OrderException;
import com.prasad.ecommerence.model.Address;
import com.prasad.ecommerence.model.Order;
import com.prasad.ecommerence.model.User;

public interface OrderService  {
    public Order createOrder(User user,Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> userOrderHistory(Long userId);
    public Order placeOrder(Long orderId) throws OrderException;
    public Order confirmOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order cancleOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
}
