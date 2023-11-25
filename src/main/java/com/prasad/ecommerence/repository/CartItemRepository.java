package com.prasad.ecommerence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.Product;

public interface CartItemRepository   extends JpaRepository<CartItem,Long>{
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart AND ci.product=:product AND  ci.userId=:userId AND ci.size =:size")
    public CartItem isCartItemExists(Cart cart, Product product, String size, Long userId);

    public CartItem getCartItemById(Long cartItemId);
    
}
