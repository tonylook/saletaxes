package com.lm.saletaxes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Tax {
    private String name;
    private String value;
    private List<Category> exempt;
}
