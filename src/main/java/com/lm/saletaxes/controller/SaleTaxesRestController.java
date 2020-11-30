package com.lm.saletaxes.controller;

import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.service.SaleTaxesService;
import com.lm.saletaxes.service.SaleTaxesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleTaxesRestController {

    SaleTaxesService saleTaxesService;

    @PostMapping("/calculateBasket")
    public ReceiptDetails calculate(Basket basket){
        //logic
        return null;
    }

    @Autowired
    public void setSaleTaxesService(SaleTaxesService saleTaxesService) {
        this.saleTaxesService = saleTaxesService;
    }
}
