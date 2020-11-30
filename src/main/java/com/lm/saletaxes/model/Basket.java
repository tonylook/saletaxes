package com.lm.saletaxes.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Basket {
    @NotNull
    private List<Product> products;
}
