package com.lm.saletaxes.dao;

import javax.persistence.*;

@Entity
@Table(name = "taxes")
public class TaxesDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    //properties
}
