package com.application.budzetKlient.rest;

import com.application.budzetKlient.dto.AddExpenseDto;
import com.application.budzetKlient.dto.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseClient {

    private final LoginClient loginClient;

    public List<ExpenseDto> getExpenses(){

        ResponseEntity<ExpenseDto[]> responseEntity = getRestTemplate().getForEntity(
                "http://localhost:8080/api/expense",
                ExpenseDto[].class);

        ExpenseDto[] arrayExpenses = responseEntity.getBody();

        return Arrays.asList(arrayExpenses);
    }

    public boolean addExpense(AddExpenseDto addExpenseDto) {

        ResponseEntity<ExpenseDto> responseEntity = getRestTemplate().postForEntity(
                "http://localhost:8080/api/expense",
                addExpenseDto,
                ExpenseDto.class);

        return responseEntity.getStatusCode().equals(HttpStatus.CREATED);
    }

    private RestTemplate getRestTemplate() {
        String accessToken = loginClient.getToken();

        return new RestTemplateBuilder(rt-> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        })).build();
    }


}
