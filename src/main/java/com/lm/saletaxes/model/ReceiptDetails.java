package com.lm.saletaxes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ReceiptDetails {
    private List<Product> products;
    private String saleTaxes;
    private String total;
}
