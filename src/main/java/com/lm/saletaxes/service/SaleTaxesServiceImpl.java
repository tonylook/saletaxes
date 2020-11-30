package com.lm.saletaxes.service;

import com.lm.saletaxes.dao.TaxDao;
import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service(value = "saleTaxesService")
public class SaleTaxesServiceImpl implements SaleTaxesService {

    TaxRepository taxRepository;

    @Override
    public ReceiptDetails calculate(Basket basket) {
        List<TaxDao> taxes = taxRepository.findAll();
        AtomicReference<ReceiptDetails> receiptDetails = new AtomicReference<>(new ReceiptDetails());
        basket.getProducts().stream().forEach(product -> {
            taxes.stream().forEach(taxDao -> {
                String importTax = "0";
                String saleTax = "0";
                if(taxDao.getExempt().stream().filter(categoryDao -> categoryDao.getName().equalsIgnoreCase(product.getCategory().getName())).collect(Collectors.toList()).isEmpty()){
                    if(taxDao.getName().equalsIgnoreCase("import tax") && product.getImported()){
                        importTax = getTaxAmount(product.getPrice(),taxDao.getValue());
                        product.setPrice(new BigDecimal(product.getPrice()).add(new BigDecimal(importTax)).toString());
                    }

                    String totalTax = new BigDecimal(importTax).add(new BigDecimal(saleTax)).toString();
                    receiptDetails.set(appendToReceipt(receiptDetails.get(), product, totalTax));
                }

            });

        });
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
