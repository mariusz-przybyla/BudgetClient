package com.application.budzetKlient.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Expense {

    private Long id;

    @NotNull
    private String createdAt;

    @NotNull
    private String name;

    @NotNull
    private double price;

    @NotNull
    private Category type;
}
