package com.prasad.ecommerence.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prasad.ecommerence.exception.ProductException;
import com.prasad.ecommerence.model.Category;
import com.prasad.ecommerence.model.Product;
import com.prasad.ecommerence.repository.CategoryRepository;
import com.prasad.ecommerence.repository.ProductRepository;
import com.prasad.ecommerence.request.CreateProductRequest;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProductServiceImplemention implements ProductService{

    private final ProductRepository productRepository;
    private final UserService UserService;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) {

        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        if(topLevel == null ){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
        if(secondLevel == null ){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);
            secondLevelCategory.setParentCategory(topLevel);
            secondLevel = categoryRepository.save(secondLevelCategory);
        }
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
        if(thirdLevel == null ){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountedPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
       return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
         Product product = findProductById(productId);
         product.getSizes().clear();
         productRepository.delete(product);
        return "product deleted succesfully";
    }

    @Override
    public Product updateProduct(Long productId,Product reqProduct) throws ProductException {
         Product product = findProductById(productId);
         if(reqProduct.getQuantity()!=0){
            product.setQuantity(reqProduct.getQuantity());
         }
         return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            return product.get();
        }
        throw new ProductException("product not found");
        
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProductByCategory'");
    }

    @Override
    public Page<Product> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock,Integer pageNumber, Integer pageSize) {
                System.out.println("getAllProduct method");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        System.out.println("category -"+category);
        System.out.println("minPrice -"+minPrice);
        System.out.println("maxPrice -"+maxPrice);
        System.out.println("minDiscount -"+minDiscount);
        System.out.println("sort -"+sort);
        System.out.println(products.size());
        if(!colors.isEmpty()){
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if(stock != null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
            }else if(stock.equals("out_of_stock")){
                products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());
        return filteredProducts;
    }

    

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    
}
