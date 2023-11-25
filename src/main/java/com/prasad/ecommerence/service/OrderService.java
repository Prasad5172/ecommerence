package com.prasad.ecommerence.service;

import java.util.List;

import com.prasad.ecommerence.exception.OrderException;
import com.prasad.ecommerence.model.Address;
import com.prasad.ecommerence.model.Orders;
import com.prasad.ecommerence.model.User;

public interface OrderService  {
    public Orders createOrder(User user,Address shippingAddress);
    public Orders findOrderById(Long orderId);
    public List<Orders> userOrderHistory(Long userId);
    public Orders placeOrder(Long orderId) throws OrderException;
    public Orders confirmOrder(Long orderId) throws OrderException;
    public Orders shippedOrder(Long orderId) throws OrderException;
    public Orders deliveredOrder(Long orderId) throws OrderException;
    public Orders cancleOrder(Long orderId) throws OrderException;
    public List<Orders> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
}
