package com.prasad.ecommerence.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.CartItemException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.CartItem;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.CartItemRepository;
import com.prasad.ecommerence.repository.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());
        if (user.getId().equals(userId)) {
            item.setQuantity(item.getQuantity());
            item.setPrice(item.getProduct().getPrice() * item.getQuantity());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        // Set<CartItem> cartCartItems = cart.getCartItems();
        // List<CartItem> cartItem = cartCartItems.stream().filter(c -> product==c.getProduct() && c.getUserId() == userId && c.getSize()== size).collect(Collectors.toList());
        // if(!cartItem.isEmpty()){
        //     return cartItem.get(0);
        // }

        // throw ne("Cart item not found for the userId:"+userId);
        CartItem cartItem = cartItemRepository.isCartItemExists(cart,product,size,userId);
        return cartItem;

    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
       CartItem cartItem = findCartItemById(cartItemId);
    //    User user = userService.findUserById(cartItem.getUserId());
    //    User reqUser = userService.findUserById(userId);
    //    if(user.getId() == reqUser.getId()){
    //         cartItemRepository.delete(cartItem);
    //    }
       if(cartItem.getUserId().equals(userId)){
        cartItemRepository.delete(cartItem);
       }else{
           throw new CartItemException("another cart cannot be removed");
       }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isPresent()){
            return cartItem.get();
        }else{
            throw new CartItemException("cart item not found"+cartItemId);
        }
    }

}
