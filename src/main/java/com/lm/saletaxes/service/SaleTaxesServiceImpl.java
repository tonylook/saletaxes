package com.lm.saletaxes.service;

import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SaleTaxesServiceImpl implements SaleTaxesService {
    @Autowired
    TaxRepository taxRepository;

    @Override
    public ReceiptDetails calculate(Basket basket) {
        return null;
    }

    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

}
