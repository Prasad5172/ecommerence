package com.prasad.ecommerence.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.model.Review;
import com.prasad.ecommerence.model.User;
import com.prasad.ecommerence.repository.ProductRepository;
import com.prasad.ecommerence.repository.ReviewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImplemenntation implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Override
    public Review createReview(ReviewRequest req,User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setLocalDateTime(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
    
}
