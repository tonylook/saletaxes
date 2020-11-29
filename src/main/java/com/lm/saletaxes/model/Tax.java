package com.lm.saletaxes.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Tax {
    private String name;
    private String value;
    private List<Category> exempt;
}
