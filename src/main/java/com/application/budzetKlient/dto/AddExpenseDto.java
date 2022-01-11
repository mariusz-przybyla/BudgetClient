package com.application.budzetKlient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddExpenseDto {

    private String name;
    private Double price;
    private Long categoryId;
}
