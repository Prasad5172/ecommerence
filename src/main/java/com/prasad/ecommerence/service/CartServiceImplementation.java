package com.prasad.ecommerence.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.CartRepository;
import com.prasad.ecommerence.request.AddItemRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService{

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;


    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart =findUserCart(userId);
        Product product = productService.findProductById(req.getProductid());
        List<CartItem> isCartItemPresent = cart.getCartItems().stream().filter(c-> c.getProduct().getId()== req.getProductid()).collect(Collectors.toList());
        if(isCartItemPresent.isEmpty()){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setSize(req.getSize());
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setPrice(req.getPrice());
            cartItem.setUserId(userId);
            cartItem.setPrice(req.getPrice()*product.getDiscountedPrice());
            CartItem cartItem2 = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(cartItem2);
        }
        return "Item added to cart";

    }
    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice =0;
        int totalDiscountedPrice =0;
        int totalItems =0;
        for(CartItem cartItem : cart.getCartItems()){
            totalPrice +=cartItem.getPrice();
            totalItems += cartItem.getQuantity();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItems);
        cart.setTotalDiscountPrice(totalDiscountedPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        return cartRepository.save(cart);

    }

    
    
}
