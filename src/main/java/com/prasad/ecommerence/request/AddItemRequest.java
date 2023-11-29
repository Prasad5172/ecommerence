package com.prasad.ecommerence.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemRequest {
    private Long productId;
    private String size;
    private int quantity;
}
