package com.prasad.ecommerence.service;

import java.util.List;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Review;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.request.ReviewRequest;

public interface ReviewService {
    public Review createReview(ReviewRequest req,User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
