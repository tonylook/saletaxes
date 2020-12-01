package com.lm.saletaxes.service;

import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.ReceiptDetails;

public interface SaleTaxesService {
    ReceiptDetails calculate(Basket basket);
}
