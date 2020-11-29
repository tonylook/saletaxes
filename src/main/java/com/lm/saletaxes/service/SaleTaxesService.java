package com.lm.saletaxes.service;

import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleTaxesService {
    @Autowired
    TaxRepository taxRepository;

    public ReceiptDetails calculate (List<Product> products){
        //logic
        return null;
    }

    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }
}
