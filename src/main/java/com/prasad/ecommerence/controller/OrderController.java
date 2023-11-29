package com.prasad.ecommerence.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.OrderException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Address;
import com.prasad.ecommerence.model.Order;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.service.OrderService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/orders")
@AllArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization")String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(order,HttpStatus.CREATED);

    }
    @GetMapping("user")
    public ResponseEntity<List<Order>> getOrderOfUser(@RequestHeader("Authorization")String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> order = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderOfUser(@RequestHeader("Authorization")String jwt,@PathVariable("id") Long orderId) throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }
    
}
