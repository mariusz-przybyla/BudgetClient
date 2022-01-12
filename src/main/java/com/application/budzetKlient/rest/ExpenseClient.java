package com.application.budzetKlient.rest;

import com.application.budzetKlient.dto.AddExpenseDto;
import com.application.budzetKlient.dto.ExpenseDto;
import com.application.budzetKlient.model.Expense;
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

    public boolean deleteExpense(Long id) {

        try {
            Map<String, Long> params = new HashMap<String, Long>();
            params.put("id", id);

            getRestTemplate().delete("http://localhost:8080/api/expense/{id}", params);

        } catch (Exception e) {
            logger.info("Usuniecie elementu nie powiodło się!");
        }

        return true;
    }

//    public void editElement(ExpenseDto expenseDto) {
//        ExpenseDto ex = new ExpenseDto();
//        ex.setName(expenseDto.getName());
//        ex.setPrice(expenseDto.getPrice());
//        ex.setType(ex.getType());
//
//        ResponseEntity<ExpenseDto> responseEntity = getRestTemplate().put(
//                "http://localhost:8080/api/expense/{id}",
//                ex,
//                expenseDto);
//    }

    private RestTemplate getRestTemplate() {
        String accessToken = loginClient.getToken();

        return new RestTemplateBuilder(rt-> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        })).build();
    }
}
