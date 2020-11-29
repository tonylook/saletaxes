package com.lm.saletaxes.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Basket {
    private List<Product> products;
}
