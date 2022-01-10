package com.application.budzetKlient.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpenseDto {

    private Long id;
    private String name;
    private Double price;
    private String type;
}
