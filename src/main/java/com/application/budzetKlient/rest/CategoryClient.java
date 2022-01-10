package com.application.budzetKlient.rest;

import com.application.budzetKlient.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryClient {

    private final RestTemplate restTemplate;

    public List<Category> getCategory(){

        ResponseEntity<Category[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:8080/api/categories",
                Category[].class);

        Category[] body = responseEntity.getBody();

        return Arrays.asList(body);
    }
}
