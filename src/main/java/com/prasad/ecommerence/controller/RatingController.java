package com.prasad.ecommerence.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.exception.UserException;
import com.prasad.ecommerence.model.Rating;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.request.RatingRequest;
import com.prasad.ecommerence.service.RatingService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/ratings")
@AllArgsConstructor
public class RatingController {
    private final UserService userService;
    private final RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,@RequestHeader("Authorization") String jwt) throws ProductException, UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req, user);
        return new ResponseEntity<>(rating,HttpStatus.CREATED);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId) throws ProductException, UserException{
        List<Rating> rating = ratingService.getProductsRating(productId);
        return new ResponseEntity<>(rating,HttpStatus.CREATED);
    }
}
