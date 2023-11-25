package com.prasad.ecommerence.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Rating;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.request.RatingRequest;

@Service
public interface RatingService {
    public Rating createRating(RatingRequest req,User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}
