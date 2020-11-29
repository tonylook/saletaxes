package com.lm.saletaxes.controller;

import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.service.SaleTaxesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleTaxesRestController {
    @Autowired
    SaleTaxesService saleTaxesService;

    @PostMapping("/calculate")
    public ReceiptDetails calculate(List<Product> products){
        //logic
        return null;
    }

    public void setSaleTaxesService(SaleTaxesService saleTaxesService) {
        this.saleTaxesService = saleTaxesService;
    }
}
