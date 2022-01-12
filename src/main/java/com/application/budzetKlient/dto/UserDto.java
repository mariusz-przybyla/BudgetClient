package com.application.budzetKlient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String createdAt;
}
