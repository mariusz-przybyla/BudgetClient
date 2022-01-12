package com.application.budzetKlient.rest;

import com.application.budzetKlient.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryClient {

    private final RestTemplate restTemplate;
    private final LoginClient loginClient;

    public List<Category> getCategory(){

        ResponseEntity<Category[]> responseEntity = getRestTemplate().getForEntity(
                "http://localhost:8080/api/categories",
                Category[].class);

        Category[] body = responseEntity.getBody();

        return Arrays.asList(body);
    }

    public List<Category> getPaymentCategory() {

        return getCategory().stream()
                .filter(p -> p.getIo() > 0)
                .collect(Collectors.toList());
    }

    public List<Category> getExpenseCategory() {

        return getCategory().stream()
                .filter(p -> p.getIo() < 0)
                .collect(Collectors.toList());
    }

    private RestTemplate getRestTemplate() {
        String accessToken = loginClient.getToken();

        return new RestTemplateBuilder(rt-> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        })).build();
    }
}
