package com.application.budzetKlient.rest;

import com.application.budzetKlient.model.LoginRequest;
import com.application.budzetKlient.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginClient {

    private final RestTemplate restTemplate;
    private LoginResponse loginResponse;

    public boolean login(String login, String password){

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(login);
        loginRequest.setPassword(password);

        ResponseEntity<LoginResponse> responseEntity = restTemplate.postForEntity(
                "http://localhost:8080/api/auth/login",
                loginRequest,
                LoginResponse.class);

         loginResponse = responseEntity.getBody();

        return responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

    public String getToken() {

        return Optional.ofNullable(loginResponse)
                .map(e -> e.getAccessToken())
                .orElse("Błąd");
    }

    public void logout() {

        loginResponse = null;
    }

    public boolean isNotLogged() {

        return loginResponse == null;
    }
}
