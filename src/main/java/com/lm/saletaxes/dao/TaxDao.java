package com.lm.saletaxes.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "taxes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String value;
    @OneToMany
    private List<CategoryDao> exempt;
}
