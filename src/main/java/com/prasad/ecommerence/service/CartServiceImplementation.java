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


        System.out.println("addCartItem");
        Cart cart = findUserCart(userId);
        Product product = productService.findProductById(req.getProductId());
        // Check if the cart item already exists
        CartItem existingCartItem = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if (existingCartItem == null) {
            System.out.println("CartItem is not present");
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setSize(req.getSize());
            cartItem.setCart(cart); // Set the Cart to the CartItem
            cartItem.setQuantity(req.getQuantity());
            cartItem.setPrice(product.getPrice() * req.getQuantity());
            cartItem.setUserId(userId);
            // Persist the new CartItem
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            // Add the created CartItem to the user's Cart
            cart.getCartItems().add(createdCartItem);
            // Save or update the Cart in the database
            cartRepository.save(cart);
            System.out.println("Cart and CartItem added to the database");
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
        return cart;

    }

    
    
}
