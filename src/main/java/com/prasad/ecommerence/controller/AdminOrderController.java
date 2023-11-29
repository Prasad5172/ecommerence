package com.prasad.ecommerence.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.OrderException;
import com.prasad.ecommerence.model.Order;
import com.prasad.ecommerence.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/admin/orders")
@AllArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }
    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt ) throws OrderException{
        Order order= orderService.confirmOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt ) throws OrderException{
        Order order= orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt ) throws OrderException{
        Order order= orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt ) throws OrderException{
        Order order= orderService.cancleOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<String> deleteOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt ) throws OrderException{
         orderService.deleteOrder(orderId);
        return new ResponseEntity<>("order deleted succesfully",HttpStatus.OK);
    }



}
