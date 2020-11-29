package com.lm.saletaxes.controller;

import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleTaxesRestController {
    @PostMapping("/calculate")
    public ReceiptDetails calculate(List<Product> products){
        //logic
        return null;
    }
}
