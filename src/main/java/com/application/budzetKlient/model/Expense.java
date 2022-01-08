package com.application.budzetKlient.model;

import lombok.Data;

@Data
public class Expense {

    private Long id;
    private String name;
    private double price;
    private String type;
}
