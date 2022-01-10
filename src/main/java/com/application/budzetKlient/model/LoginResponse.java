package com.application.budzetKlient.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String login;
}
