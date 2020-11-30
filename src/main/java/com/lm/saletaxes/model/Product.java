package com.lm.saletaxes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Product {
    private Integer quantity;
    private String name;
    private Boolean imported;
    private String price;
    private Category category;
}
