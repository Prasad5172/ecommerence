package com.prasad.ecommerence.controller;

import java.util.List;

import org.hibernate.validator.cfg.defs.pl.REGONDef;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest),HttpStatus.CREATED);
    }

   

    @PutMapping("{productId}/update")
    public ResponseEntity<Product> updateProductByid(Long productId,Product reqProduct) throws ProductException{
        return new ResponseEntity<>(productService.updateProduct(productId,reqProduct),HttpStatus.OK);
    }

    @DeleteMapping("{productId}/delete") 
    public String deleteProduct(@PathVariable("productId") Long productId) throws ProductException{
        return productService.deleteProduct(productId);
    }

    @GetMapping("all") 
    public ResponseEntity<List<Product>> findAllProduct(){
        return new ResponseEntity<>(productService.findAllProduct(),HttpStatus.OK);
    }

    @PostMapping("creates")
    public ResponseEntity<String> createMultipleProduct(@RequestBody CreateProductRequest[] req){

        for(CreateProductRequest product: req){
            productService.createProduct(product);
        }
        return new ResponseEntity<>("Product added succesfully",HttpStatus.CREATED);
    }


    

}
