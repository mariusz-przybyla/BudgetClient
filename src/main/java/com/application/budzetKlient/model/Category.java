package com.application.budzetKlient.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Category {

    @Id
    private Long id;
    private Integer io;
    private String name;
}
