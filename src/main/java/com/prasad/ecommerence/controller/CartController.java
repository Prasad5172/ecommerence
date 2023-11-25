package com.prasad.ecommerence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.service.CartService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/cart")
@AllArgsConstructor
public class CartController {
    private final UserService userService;
    private final CartService cartService;

    @GetMapping
    public Cart getUserCartBytoken(@RequestHeader("Authroization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        return cartService.findUserCart(user.getId());
    }
    
}
