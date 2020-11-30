package com.lm.saletaxes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Basket {
    private List<Product> products;
}
