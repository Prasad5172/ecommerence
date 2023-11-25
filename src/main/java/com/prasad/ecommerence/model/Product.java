package com.prasad.ecommerence.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     private String title;
     private String description;
     private int price;
     private int discountedPrice;
     private int discountPercent;
     private int quantity;
     private String brand;
     private String color;

     @Embedded
     @ElementCollection
     @Column(name = "sizes")
     private Set<Size> sizes = new HashSet<>();

     private String imageUrl;

     @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
     private List<Rating>rating = new ArrayList<>(); 
     
     @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
     private List<Review> reviews = new ArrayList<>();
     private int numRatings;

     @ManyToOne
     @JoinColumn(name = "category_id")
     private Category category;

     private LocalDateTime createdAt;


}
