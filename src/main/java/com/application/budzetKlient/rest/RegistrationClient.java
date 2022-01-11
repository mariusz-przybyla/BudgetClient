package com.application.budzetKlient.rest;

import com.application.budzetKlient.dto.UserDto;
import com.application.budzetKlient.model.LoginRequest;
import com.application.budzetKlient.model.LoginResponse;
import com.application.budzetKlient.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationClient {

    private final RestTemplate restTemplate;

    public boolean registration(String login, String password, String firstName, String lastName){

        ResponseEntity<UserDto> responseEntity = null;

        UserDetails user = new UserDetails();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        try {
            responseEntity = restTemplate.postForEntity(
                    "http://localhost:8080/api/registration",
                    user,
                    UserDto.class);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return responseEntity.getStatusCode().equals(HttpStatus.OK);
    }
}
