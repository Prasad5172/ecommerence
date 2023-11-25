package com.prasad.ecommerence.service;

import com.prasad.ecommerence.exception.CartItemException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.model.User;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException,UserException;
    public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
    
}
