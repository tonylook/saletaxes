package com.lm.saletaxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.saletaxes.dao.CategoryDao;
import com.lm.saletaxes.dao.TaxDao;
import com.lm.saletaxes.model.Basket;
import com.lm.saletaxes.model.ReceiptDetails;
import com.lm.saletaxes.repository.TaxRepository;
import com.lm.saletaxes.service.SaleTaxesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleTaxesServiceTests {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    SaleTaxesServiceImpl saleTaxesService;

    List<TaxDao> taxes = new ArrayList<>();

    @BeforeEach
    public void init(){
        //Mocking Categogies exempt from sale tax
        List<CategoryDao> saleExemptCategories = new ArrayList<>();
        CategoryDao book = CategoryDao.builder().id(1L).name("book").build();
        CategoryDao food = CategoryDao.builder().id(2L).name("food").build();
        CategoryDao medical = CategoryDao.builder().id(3L).name("medical").build();
        saleExemptCategories.add(book);
        saleExemptCategories.add(food);
        saleExemptCategories.add(medical);

        //Mocking Categories exempt from import tax (empty)
        List<CategoryDao> importExemptCategories = new ArrayList<>();

        //Mocking the Taxes with exemptions inside for the tax
        TaxDao saleTax = TaxDao.builder().id(1L).name("sale tax").value("10").exempt(saleExemptCategories).build();
        TaxDao importTax = TaxDao.builder().id(2L).name("import tax").value("5").exempt(importExemptCategories).build();
        taxes.add(saleTax);
        taxes.add(importTax);
    }

    @Test
    public void testCalc1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        Basket basket = objectMapper.readValue(new File(classLoader.getResource("input1.json").getFile()), Basket.class);
        ReceiptDetails expected = objectMapper.readValue(new File(classLoader.getResource("output1.json").getFile()), ReceiptDetails.class);
        when(taxRepository.findAll()).thenReturn(taxes);

        ReceiptDetails receiptDetails = saleTaxesService.calculate(basket);

        verify(taxRepository, times(1)).findAll();

        assertNotNull(receiptDetails);
        assertEquals(expected,receiptDetails);
    }

    @Test
    public void testCalc2() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        Basket basket = objectMapper.readValue(new File(classLoader.getResource("input2.json").getFile()), Basket.class);
        ReceiptDetails expected = objectMapper.readValue(new File(classLoader.getResource("output2.json").getFile()), ReceiptDetails.class);
        when(taxRepository.findAll()).thenReturn(taxes);

        ReceiptDetails receiptDetails = saleTaxesService.calculate(basket);

        verify(taxRepository, times(1)).findAll();

        assertNotNull(receiptDetails);
        assertEquals(expected,receiptDetails);
    }

    @Test
    public void testCalc3() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        Basket basket = objectMapper.readValue(new File(classLoader.getResource("input3.json").getFile()), Basket.class);
        ReceiptDetails expected = objectMapper.readValue(new File(classLoader.getResource("output3.json").getFile()), ReceiptDetails.class);
        when(taxRepository.findAll()).thenReturn(taxes);

        ReceiptDetails receiptDetails = saleTaxesService.calculate(basket);

        verify(taxRepository, times(1)).findAll();

        assertNotNull(receiptDetails);
        assertEquals(expected,receiptDetails);
    }

    @Test
    public void testQuantity() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        Basket basket = objectMapper.readValue(new File(classLoader.getResource("quantity.json").getFile()), Basket.class);
        ReceiptDetails expected = objectMapper.readValue(new File(classLoader.getResource("quantityOut.json").getFile()), ReceiptDetails.class);
        when(taxRepository.findAll()).thenReturn(taxes);

        ReceiptDetails receiptDetails = saleTaxesService.calculate(basket);

        verify(taxRepository, times(1)).findAll();

        assertNotNull(receiptDetails);
        assertEquals(expected,receiptDetails);
    }
}
