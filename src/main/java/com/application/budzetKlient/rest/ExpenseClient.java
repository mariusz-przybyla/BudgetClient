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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ExpenseClient {

    private final LoginClient loginClient;
    private RestTemplate restTemplate = new RestTemplate();
    Logger logger = Logger.getLogger(ExpenseClient.class.getName());

    public List<ExpenseDto> getExpenses() {

        try {
            ResponseEntity<ExpenseDto[]> responseEntity = getRestTemplate().getForEntity(
                    "http://localhost:8080/api/expense",
                    ExpenseDto[].class);

            ExpenseDto[] arrayExpenses = responseEntity.getBody();


            return Arrays.asList(arrayExpenses);
        } catch (Exception e) {
            logger.info("Pobieranie wydatków nie powiodło się");
            return null;
        }
    }

    public List<ExpenseDto> getExpense(Long id) {

        try {
            ResponseEntity<ExpenseDto> responseEntity = getRestTemplate().getForEntity(
                    "http://localhost:8080/api/expense/{id}",
                    ExpenseDto.class,
                    id);

            ExpenseDto arrayExpenses = responseEntity.getBody();


            return Arrays.asList(arrayExpenses);
        } catch (Exception e) {
            logger.info("Pobieranie wydatków nie powiodło się");
            return null;
        }
    }

    public boolean addExpense(AddExpenseDto addExpenseDto) {

        ResponseEntity<ExpenseDto> responseEntity = null;

        try {
            responseEntity = getRestTemplate().postForEntity(
                    "http://localhost:8080/api/expense",
                    addExpenseDto,
                    ExpenseDto.class);
        } catch (Exception e) {
            logger.info("Dodanie itemu nie powiodło się!");
        }

        return responseEntity.getStatusCode().equals(HttpStatus.CREATED);
    }

    public boolean deleteExpense(Long id) {

        try {
            Map<String, Long> params = new HashMap<String, Long>();
            params.put("id", id);

            getRestTemplate().delete("http://localhost:8080/api/expense/{id}", params);

        } catch (Exception e) {
            logger.info("Usuniecie elementu powiodło się!");
        }

        return true;
    }

    public boolean updateElement(Long id, AddExpenseDto expenseDto) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        try {
            getRestTemplate().put(
                    "http://localhost:8080/api/expense/{id}",
                    expenseDto,
                    id);
            return true;
        } catch (Exception e) {
            logger.info("Edycja elementu nie powiodła się!");
            e.getStackTrace();
            return false;
        }
    }

    private RestTemplate getRestTemplate() {
        String accessToken = loginClient.getToken();

        return new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        })).build();
    }
}
