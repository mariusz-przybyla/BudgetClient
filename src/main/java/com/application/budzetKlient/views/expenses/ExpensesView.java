package com.application.budzetKlient.views.expenses;

import com.application.budzetKlient.model.Category;
import com.application.budzetKlient.model.Expense;
import com.application.budzetKlient.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Moje wydatki")
@Route(value = "expenses", layout = MainLayout.class)
public class ExpensesView extends VerticalLayout {

    private Grid<Expense> gridExpense = new Grid<>(Expense.class);
    private Button button = new Button("Dodaj wydatek");
    private List<Expense> expensesList = new ArrayList<>();
    private MenuBar menuBar = new MenuBar();
    private Category category = new Category();



    public ExpensesView() {
        add(button, gridExpense);

        Expense expense = new Expense();
        expense.setId(1l);
        expense.setName("Paliwo do audi");
        expense.setPrice(199);
        expense.setType("Paliwo");
        expensesList.add(expense);

        gridExpense.setItems(expensesList);

        button.addClickListener(event -> addItem());

    }

    private void addItem() {
        Expense expense = new Expense();
        expense.setId((long) (expensesList.size()+1));
        expense.setName("Paliwo do bmw");
        expense.setPrice(350);
        expense.setType("Paliwo");

        expensesList.add(expense);

        gridExpense.getDataProvider().refreshAll();
    }
}
