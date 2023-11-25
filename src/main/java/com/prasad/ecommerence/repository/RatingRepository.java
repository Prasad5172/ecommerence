package com.prasad.ecommerence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prasad.ecommerence.model.Rating;

public interface RatingRepository extends JpaRepository<Rating,Long>{
    @Query("""
            select r from Rating r where r.product.id = :productId
            """)
    List<Rating> getProductsRating(@Param("productId")Long productId);
    
}
