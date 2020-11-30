package com.lm.saletaxes.service;

import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service(value = "saleTaxesService")
public class SaleTaxesServiceImpl implements SaleTaxesService {

    TaxRepository taxRepository;

    @Override
    public ReceiptDetails calculate(Basket basket) {
//        List<TaxDao> taxes = taxRepository.findAll();
//        ReceiptDetails receiptDetails = new ReceiptDetails();
//        basket.getProducts().stream().map(product -> {
//            taxes.stream().filter(taxDao -> taxDao.getName().equalsIgnoreCase(product.getName()))
//                    .collect(Collectors.toList()).forEach(taxDao -> {product.get});
//        })
//        receiptDetails.set
//        BigDecimal price;
        return null;
    }

    private String getTaxAmount (String price, String tax){
        BigDecimal priceBd = new BigDecimal(price);
        BigDecimal taxBd = new BigDecimal(tax);
        return priceBd.multiply(taxBd).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    private ReceiptDetails appendToReceipt(ReceiptDetails receipt, Product product, String taxAmount){
        if(receipt.getSaleTaxes().isEmpty()) receipt.setSaleTaxes("0.00");
        if(receipt.getTotal().isEmpty()) receipt.setTotal("0.00");
        receipt.getProducts().add(product);
        BigDecimal wholePrice = new BigDecimal(product.getPrice()).multiply(new BigDecimal(product.getQuantity().toString()));
        receipt.setTotal(new BigDecimal(receipt.getTotal()).add(wholePrice).toString());
        receipt.setSaleTaxes(new BigDecimal(receipt.getSaleTaxes()).add(new BigDecimal(taxAmount)).toString());
        return receipt;
    }

    @Autowired
    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

}
