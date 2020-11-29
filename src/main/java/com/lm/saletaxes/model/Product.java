package com.lm.saletaxes.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private Integer quantity;
    private String name;
    private Category category;
    private Boolean imported;
    private String price;
}
