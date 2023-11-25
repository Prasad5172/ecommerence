package com.prasad.ecommerence.controller;

import java.net.http.HttpRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.config.JwtUtils;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Cart;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.service.CartService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("users/profile")
    public User getUserProfile(@RequestHeader("Authorization") String authorizationHeader) throws UserException{
        User user = userService.findUserProfileByJwt(authorizationHeader);
        return user;
    }
}
