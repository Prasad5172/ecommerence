package com.prasad.ecommerence.controller;

import java.util.List;
import java.lang.String;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findProductsByCategoryHandler(
            @RequestParam String category, @RequestParam List<String> color,
            @RequestParam List<String> size, @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
            @RequestParam String sort, @RequestParam String stock,
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
                System.out.println(category+" - category");
        Page<Product> res = productService.getAllProducts(category, color, size, minPrice, maxPrice, minDiscount, sort,
                stock, pageNumber, pageSize);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable("productId") Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    // @GetMapping("/products/search")
    // public ResponseEntity<Product> searchProductsHandler(@RequestParam String q) throws ProductException {
    //     Product product = productService.serachProduct(q);
    //     return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    // }

}
