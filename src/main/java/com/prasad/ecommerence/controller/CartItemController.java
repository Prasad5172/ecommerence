package com.prasad.ecommerence.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.CartItemException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.service.CartItemService;
import com.prasad.ecommerence.service.CartService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/cart_item")
@AllArgsConstructor
public class CartItemController {
    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @DeleteMapping("{id}")
    public String removeCartItem(@RequestHeader("Authorization") String jwt,@PathVariable("id") Long CartItemId) throws CartItemException, UserException{
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), CartItemId);
        return "CartItem is deleted ";
    }

    @PutMapping("cartItemId")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,
    @PathVariable("cartItemId") Long cartItemId,
    @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updateCartItem,HttpStatus.OK);
    }
    
}
