package com.lm.saletaxes.controller;

import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.service.SaleTaxesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SaleTaxesRestController {

    SaleTaxesService saleTaxesService;

    @PostMapping(value = "/calculate/basket")
    public ReceiptDetails calculate(@Valid @RequestBody Basket basket){
        try{
            return saleTaxesService.calculate(basket);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Something wrong happened");
        }
    }

    @Autowired
    public void setSaleTaxesService(SaleTaxesService saleTaxesService) {
        this.saleTaxesService = saleTaxesService;
    }
}
