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
import com.prasad.ecommerence.model.Review;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.request.ReviewRequest;
import com.prasad.ecommerence.service.ReviewService;
import com.prasad.ecommerence.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);
        return new ResponseEntity<>(review,HttpStatus.CREATED);
    }
    @GetMapping("product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId) throws UserException, ProductException{
        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews,HttpStatus.CREATED);
    }
}
