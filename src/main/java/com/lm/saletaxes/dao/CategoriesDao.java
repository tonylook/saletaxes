package com.lm.saletaxes.dao;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoriesDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    //properties
}
