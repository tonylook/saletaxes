package com.lm.saletaxes.service;

import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.ReceiptDetails;
import org.springframework.stereotype.Service;


public interface SaleTaxesService {

    ReceiptDetails calculate(Basket basket);
}
