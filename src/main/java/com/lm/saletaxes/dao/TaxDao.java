package com.lm.saletaxes.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "taxes")
@Data
public class TaxDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String value;
    @OneToMany
    private List<CategoryDao> exempt;
}
