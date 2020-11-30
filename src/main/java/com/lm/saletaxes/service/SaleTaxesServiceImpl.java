package com.lm.saletaxes.service;

import com.lm.saletaxes.dao.TaxDao;
import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.Product;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service(value = "saleTaxesService")
public class SaleTaxesServiceImpl implements SaleTaxesService {

    TaxRepository taxRepository;

    @Override
    public ReceiptDetails calculate(Basket basket) {
        List<TaxDao> taxes = taxRepository.findAll();
        List<Product> initProd = new ArrayList<>();
        ReceiptDetails init = new ReceiptDetails();
        init.setSaleTaxes("0");
        init.setTotal("0");
        init.setProducts(initProd);
        AtomicReference<ReceiptDetails> receiptDetails = new AtomicReference<>(new ReceiptDetails());
        receiptDetails.set(init);
        basket.getProducts().forEach(product -> {
            AtomicReference<String> importTax = new AtomicReference<>("0");
            AtomicReference<String> saleTax = new AtomicReference<>("0");
            taxes.forEach(taxDao -> {
                if(taxDao.getName().equalsIgnoreCase("import tax") && product.getImported()){
                    importTax.set(getTaxAmount(product.getPrice(), taxDao.getValue()));
                }
                if(taxDao.getExempt().stream().filter(categoryDao -> categoryDao.getName().equalsIgnoreCase(product.getCategory().getName())).collect(Collectors.toList()).isEmpty() && !taxDao.getName().equalsIgnoreCase("import tax")){
                    saleTax.set(getTaxAmount(product.getPrice(), taxDao.getValue()));
                    }
            });
            String totalTax = new BigDecimal(importTax.get()).add(new BigDecimal(saleTax.get())).toString();
            product.setPrice(new BigDecimal(product.getPrice()).add(new BigDecimal(importTax.get()).add(new BigDecimal(saleTax.get()))).toString());
            receiptDetails.set(appendToReceipt(receiptDetails.get(), product, totalTax));
        });
        return receiptDetails.get();
    }

    private String getTaxAmount (String price, String tax){
        BigDecimal priceBd = new BigDecimal(price);
        BigDecimal taxBd = new BigDecimal(tax);
        // Old and not working BigDecimal amount = priceBd.multiply(taxBd).divide(new BigDecimal("100"), 2, BigDecimal.HALF_EVEN).toString();
        //honestly, I found this solution on StackOverflow and works.
        BigDecimal amount = priceBd.multiply(taxBd).divide(new BigDecimal("100"));
        BigDecimal result =  new BigDecimal(Math.ceil(amount.doubleValue() * 20) / 20);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    private ReceiptDetails appendToReceipt(ReceiptDetails receipt, Product product, String taxAmount){
        if(receipt.getSaleTaxes().isEmpty()) receipt.setSaleTaxes("0.00");
        if(receipt.getTotal().isEmpty()) receipt.setTotal("0.00");
        receipt.getProducts().add(product);
        BigDecimal wholePrice = new BigDecimal(product.getPrice()).multiply(new BigDecimal(product.getQuantity().toString()));
        receipt.setTotal(new BigDecimal(receipt.getTotal()).add(wholePrice).toString());
        receipt.setSaleTaxes(new BigDecimal(receipt.getSaleTaxes()).add(new BigDecimal(taxAmount)).multiply(new BigDecimal(product.getQuantity().toString())).toString());
        return receipt;
    }

    @Autowired
    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

}
