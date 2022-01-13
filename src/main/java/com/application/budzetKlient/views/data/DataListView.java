package com.application.budzetKlient.views.data;

import com.application.budzetKlient.dto.ExpenseDto;
import com.application.budzetKlient.rest.ExpenseClient;
import com.application.budzetKlient.views.MainLayoutView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.*;
import java.util.stream.Collectors;

@PageTitle("Dane")
@Route(value = "data", layout = MainLayoutView.class)
public class DataListView extends Div {

    private HorizontalLayout horizontalIncomeLayout = new HorizontalLayout();
    private HorizontalLayout horizontalSummaryIncomLayout = new HorizontalLayout();

    private HorizontalLayout horizontalOneExpensesLayout = new HorizontalLayout();
    private HorizontalLayout horizontalTwoExpensesLayout = new HorizontalLayout();
    private HorizontalLayout horizontalSummaryExpLayout = new HorizontalLayout();

    private VerticalLayout verticalLayout = new VerticalLayout();

    NumberField SummaryField = new NumberField();

    private ExpenseClient expenseClient;

    private double summaryIncome = 0;
    private double summaryExpense = 0;

    public DataListView(ExpenseClient expenseClient) {

        this.expenseClient = expenseClient;

        horizontalIncomeLayout.add(
                displayIncome("Wynagrodzenie", "200px", "Wynagrodzenie"),
                displayIncome("Wygrana lotto", "200px", "Wygrana lotto"),
                displayIncome("Premia", "200px", "Premia"),
                displayIncome("Pozostały przychód", "200px", "Pozostały przychód")
        );
        horizontalSummaryIncomLayout.add(
                displaySummary("Suma przychodów", "200px", summaryIncome)
        );

        horizontalOneExpensesLayout.add(
                displayExtracts("Wydatki domowe", "200px", "Dom"),
                displayExtracts("Paliwo", "200px", "Paliwo"),
                displayExtracts("Wakacje", "200px", "Wakacje"),
                displayExtracts("Weekendowe wypady", "200px", "Weekendowe wypady"),
                displayExtracts("Narzędzia", "200px", "Narzędzia")
        );
        horizontalTwoExpensesLayout.add(
                displayExtracts("Ogród", "200px", "Ogród"),
                displayExtracts("Samochód", "200px", "Samochód"),
                displayExtracts("Ubrania", "200px", "Ubrania"),
                displayExtracts("Elektronika", "200px", "Elektronika"),
                displayExtracts("Pozostałe wydatki", "200px", "Pozostałe wydatki")
        );
        horizontalSummaryExpLayout.add(
                displaySummary("Suma wydatków", "200px", summaryExpense)
        );

        SummaryField.setReadOnly(true);
        SummaryField.setLabel("Podsumowanie dochodu");
        SummaryField.setWidth("200px");
        SummaryField.setValue(summaryIncome - summaryExpense);

        verticalLayout.add(
                horizontalIncomeLayout,
                horizontalSummaryIncomLayout,
                horizontalOneExpensesLayout,
                horizontalTwoExpensesLayout,
                horizontalSummaryExpLayout,
                SummaryField
        );

        add(verticalLayout);
    }

    private NumberField displayIncome (String labelName, String setWidth, String nameElement) {
        NumberField numberField = new NumberField();

        numberField.setReadOnly(true);
        numberField.setLabel(labelName);
        numberField.setWidth(setWidth);
        numberField.setValue(extractIncomeElements(nameElement));

        return numberField;
    }

    private double extractIncomeElements(String element) {

        double result = 0;

        List<ExpenseDto> expenses = expenseClient.getExpenses().stream()
                .filter(e -> e.getType().equals(element))
                .collect(Collectors.toList());

        for (ExpenseDto list : expenses) {
            result += list.getPrice();
        }
        summaryIncome += result;

        return result;
    }

    private NumberField displayExtracts(String labelName, String setWidth, String nameElement) {
        NumberField numberField = new NumberField();

        numberField.setReadOnly(true);
        numberField.setLabel(labelName);
        numberField.setWidth(setWidth);
        numberField.setValue(extractExpensesElements(nameElement));

        return numberField;
    }

    private double extractExpensesElements(String element) {

        double result = 0;

        List<ExpenseDto> expenses = expenseClient.getExpenses().stream()
                .filter(e -> e.getType().equals(element))
                .collect(Collectors.toList());

        for (ExpenseDto list : expenses) {
            result += list.getPrice();
        }
        summaryExpense += result;

        return result;
    }

    private NumberField displaySummary (String labelName, String setWidth, double summary) {
        NumberField numberField = new NumberField();

        numberField.setReadOnly(true);
        numberField.setLabel(labelName);
        numberField.setWidth(setWidth);
        numberField.setValue(summary);

        return numberField;
    }
}





