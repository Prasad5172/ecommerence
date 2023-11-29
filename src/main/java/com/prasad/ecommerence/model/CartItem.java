package com.prasad.ecommerence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore  // used to prevent from the infnite recursion
    @ManyToOne
    @JoinColumn(name = "cart_id") // Define the foreign key column
    private Cart cart;

    @ManyToOne
    private Product product;


    private String size;
    private Integer quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
}
