package com.prasad.ecommerence.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.request.CreateProductRequest;
import com.prasad.ecommerence.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/admin/products")
@AllArgsConstructor
public class AdminProductController {

    private final ProductService productService;


    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    // public String deleteProduct(Long productId) throws ProductException;
    // public Product updateProduct(Long productId,Product reqProduct) throws ProductException;


    @PutMapping
    public Product updateProductByid(Long productId,Product reqProduct) throws ProductException{
        return productService.updateProduct(productId,reqProduct);
    }

    @DeleteMapping("{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) throws ProductException{
        return productService.deleteProduct(productId);
    }

}
