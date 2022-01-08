package com.application.budzetKlient.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

@Data
public class UserDetails {

    private Long id;

    @NotNull
    @Length(min = 4, max = 64)
    private String login;

    @NotNull
    @Length(min = 8, max = 64)
    private String password;

    @NotNull
    @Length(min = 1, max = 32)
    private String firstname;

    @NotNull
    @Length(min = 1, max = 32)
    private String lastname;
}