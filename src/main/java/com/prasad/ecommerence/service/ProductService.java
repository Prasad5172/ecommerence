package com.prasad.ecommerence.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.request.CreateProductRequest;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);
    public String deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,Product reqProduct) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    public List<Product> findProductByCategory(String category);
    public Page<Product> getAllProducts(String category,List<String> colors,List<String>sizes,Integer minPrice,Integer maxPrice,Integer minDiscount,String sort,String Stock,Integer pageNumber,Integer pageSize ); 
}
