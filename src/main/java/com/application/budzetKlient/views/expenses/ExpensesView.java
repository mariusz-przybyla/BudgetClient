package com.application.budzetKlient.views.expenses;

import com.application.budzetKlient.model.Category;
import com.application.budzetKlient.dto.ExpenseDto;
import com.application.budzetKlient.rest.ExpenseClient;
import com.application.budzetKlient.rest.LoginClient;
import com.application.budzetKlient.views.MainLayoutView;
import com.application.budzetKlient.views.login.LogoutView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@PageTitle("Moje wydatki")
@Route(value = "expenses", layout = MainLayoutView.class)
public class ExpensesView extends VerticalLayout {

    private Grid<ExpenseDto> gridExpense1 = new Grid<>(ExpenseDto.class, false);


    private Button button = new Button("Dodaj wydatek");
    private MenuBar menuBar = new MenuBar();
    private Category category = new Category();

    private ExpenseClient expenseClient;

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String localDateTime = LocalDateTime.now().format(format);


    public ExpensesView(ExpenseClient expenseClient, LoginClient loginClient) {

        this.expenseClient = expenseClient;

        if (loginClient.isNotLogged()) {
            UI.getCurrent().navigate(LogoutView.class);
        }

        gridExpense1.setSelectionMode(Grid.SelectionMode.MULTI);
        gridExpense1.addColumn(ExpenseDto::getId).setHeader("Id").setSortable(true);
        gridExpense1.addColumn(ExpenseDto::getName).setHeader("Nazwa").setSortable(true);
        gridExpense1.addColumn(ExpenseDto::getPrice).setHeader("Cena").setSortable(true);
        gridExpense1.addColumn(ExpenseDto::getType).setHeader("Typ").setSortable(true);
        gridExpense1.addColumn(ExpenseDto::getCreatedAt).setHeader("Data").setSortable(true);
//        gridExpense1.addColumn(Expense::getProfession).setHeader("Profession")
//                .setSortable(true);
//        gridExpense1.addColumn(new LocalDateRenderer<>(GridSorting::getPersonBirthday,
//                "yyyy-MM-dd")).setHeader("Birthday").setSortable(true)
//                .setComparator(Expense::getBirthday);
        gridExpense1.addSelectionListener(selectionEvent -> {
           //np usuwanie elementu
        });

        add(button, gridExpense1);



        if (loginClient.isNotLogged()) {
            gridExpense1.setItems(Collections.emptyList());
        } else {
            gridExpense1.setItems(expenseClient.getExpenses());
        }

        button.addClickListener(event -> {
            UI.getCurrent().navigate("expense");
        });
    }
}
