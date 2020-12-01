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
        List<TaxDao> taxes = taxRepository.findAll(); //here I get all the taxes applicable with the exemptions
        ReceiptDetails init = new ReceiptDetails(new ArrayList<>(),"0","0"); //here I init an empty object to avoid null pointer
        AtomicReference<ReceiptDetails> receiptDetails = new AtomicReference<>(new ReceiptDetails()); //Here i create the object for the lambdas
        receiptDetails.set(init); //here i set the empty object in the object for lambdas
        basket.getProducts().forEach(product -> { //here i iterate on all products
            AtomicReference<String> importTax = new AtomicReference<>("0");
            AtomicReference<String> saleTax = new AtomicReference<>("0");
            taxes.forEach(taxDao -> { //here i iterate on all the taxes and apply these updating the price
                if(taxDao.getName().equalsIgnoreCase("import tax") && product.getImported()){ //here I apply the import tax
                    importTax.set(getTaxAmount(product.getPrice(), taxDao.getValue()));
                }
                //if an exempt category matches the category of the product (I generate a list and check if the list is empty or not)
                //if the list is empty and the name of the tax is not "import tax" apply the tax, if is not empty don't apply the tax because the product is exempt
                if(taxDao.getExempt().stream().filter(categoryDao -> categoryDao.getName().equalsIgnoreCase(product.getCategory().getName())).collect(Collectors.toList()).isEmpty() && !taxDao.getName().equalsIgnoreCase("import tax")){
                    saleTax.set(new BigDecimal(saleTax.get()).add(new BigDecimal(getTaxAmount(product.getPrice(), taxDao.getValue()))).toString());
                    }
            });
            //here I store the total taxes applied to the basket
            String totalTax = new BigDecimal(importTax.get()).add(new BigDecimal(saleTax.get())).toString();
            product.setPrice(new BigDecimal(product.getPrice()).add(new BigDecimal(importTax.get()).add(new BigDecimal(saleTax.get()))).toString());
            receiptDetails.set(appendToReceipt(receiptDetails.get(), product, totalTax));
        });
        return receiptDetails.get();
    }

    private String getTaxAmount (String price, String tax){
        //here is the method where I get the amount of the tax and apply the rounding logic (0.05)
        BigDecimal priceBd = new BigDecimal(price);
        BigDecimal taxBd = new BigDecimal(tax);
        BigDecimal amount = priceBd.multiply(taxBd).divide(new BigDecimal("100"));
        BigDecimal result =  new BigDecimal(Math.ceil(amount.doubleValue() * 20) / 20);
//        BigDecimal result =  new BigDecimal(amount.multiply(new BigDecimal("20").setScale(9,RoundingMode.CEILING)).divide(new BigDecimal("20")).toString());
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    private ReceiptDetails appendToReceipt(ReceiptDetails receipt, Product product, String taxAmount){
        //here is where i build the final object
        receipt.getProducts().add(product);
        BigDecimal wholePrice = new BigDecimal(product.getPrice()).multiply(new BigDecimal(product.getQuantity().toString())); //here i build the price based on the quantity
        receipt.setTotal(new BigDecimal(receipt.getTotal()).add(wholePrice).toString());
        receipt.setSaleTaxes(new BigDecimal(receipt.getSaleTaxes()).add(new BigDecimal(taxAmount)).multiply(new BigDecimal(product.getQuantity().toString())).toString());
        return receipt;
    }

    @Autowired
    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

}
