package com.prasad.ecommerence.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.model.Rating;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.RatingRepository;
import com.prasad.ecommerence.request.RatingRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService{

    private final RatingRepository ratingRepository;
    private final ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getProductsRating(productId);
    }
    
}
