package com.prasad.ecommerence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.request.AddItemRequest;
import com.prasad.ecommerence.service.CartItemService;
import com.prasad.ecommerence.service.CartService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/cart")
@AllArgsConstructor
public class CartController {

    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping
    public Cart getUserCartBytoken(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        System.out.println(user.getEmail());
        return cartService.findUserCart(user.getId());
    }
    
    @PutMapping("add")
    public String addCartItemToCart(@RequestHeader("Authorization") String jwt , @RequestBody AddItemRequest itemRequest) throws UserException, ProductException{
        System.out.println("     -addCartItemtocart");
        User user = userService.findUserProfileByJwt(jwt);
        return cartService.addCartItem(user.getId(),itemRequest);
    }
    
}
