package com.prasad.ecommerence.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.request.AddItemRequest;

public interface CartService  {
    public Cart createCart(User user);
    public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
