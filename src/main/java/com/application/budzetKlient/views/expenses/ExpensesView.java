package com.application.budzetKlient.views.expenses;

import com.application.budzetKlient.dto.ExpenseDto;
import com.application.budzetKlient.model.Category;
import com.application.budzetKlient.rest.CategoryClient;
import com.application.budzetKlient.rest.ExpenseClient;
import com.application.budzetKlient.rest.LoginClient;
import com.application.budzetKlient.views.MainLayoutView;
import com.application.budzetKlient.views.login.LogoutView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Moje wydatki")
@Route(value = "expenses", layout = MainLayoutView.class)
public class ExpensesView extends VerticalLayout {

    private Grid<ExpenseDto> gridExpense = new Grid<>(ExpenseDto.class, false);

    private Button addExpnseButton = new Button("Dodaj wydatek");
    private Button deleteButton = new Button("Usuń element");
    private Button editButton = new Button("Edytuj element");

    private TextField nameSearch = new TextField();
    private ComboBox<Category> categorySearch = new ComboBox<>();

    private ExpenseClient expenseClient;
    private CategoryClient categoryClient;
    private ExpenseDto expenseDto;

    public ExpensesView(ExpenseClient expenseClient, LoginClient loginClient, CategoryClient categoryClient) {

        this.expenseClient = expenseClient;
        this.categoryClient = categoryClient;

        if (loginClient.isNotLogged()) {
            UI.getCurrent().navigate(LogoutView.class);
        }

        gridExpense.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridExpense.addColumn(ExpenseDto::getId).setHeader("Id").setSortable(true);
        gridExpense.addColumn(ExpenseDto::getName).setHeader("Nazwa").setSortable(true);
        gridExpense.addColumn(ExpenseDto::getPrice).setHeader("Cena").setSortable(true);
        gridExpense.addColumn(ExpenseDto::getType).setHeader("Typ").setSortable(true);
        gridExpense.addColumn(ExpenseDto::getCreatedAt).setHeader("Data").setSortable(true);

        gridExpense.addSelectionListener(selectionEvent -> {
            deleteButton.addClickListener(e -> selectionEvent.getFirstSelectedItem().ifPresent(item -> deleteItem(item.getId())));
            editButton.addClickListener(e -> selectionEvent.getFirstSelectedItem().ifPresent(item -> deleteItem(item.getId())));
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        horizontalLayout1.add(addExpnseButton, editButton, deleteButton);
        horizontalLayout2.add(nameSearch, categorySearch);
        verticalLayout.add(horizontalLayout1, horizontalLayout2);

        nameSearch.setPlaceholder("Szukaj po nazwie");
        nameSearch.setValueChangeMode(ValueChangeMode.EAGER);
        nameSearch.setClearButtonVisible(true);
        nameSearch.addValueChangeListener(e -> updateListName());

        categorySearch.setPlaceholder("Szukaj po kategorii");
        categorySearch.setClearButtonVisible(true);
        categorySearch.setItems(categoryClient.getCategory());
        categorySearch.setItemLabelGenerator(e -> e.getName());
        categorySearch.addValueChangeListener(e -> updateListType());

        addExpnseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add(verticalLayout, gridExpense);

        if (loginClient.isNotLogged()) {
            gridExpense.setItems(Collections.emptyList());
        } else {
            gridExpense.setItems(expenseClient.getExpenses());
        }

        addExpnseButton.addClickListener(event -> {
            UI.getCurrent().navigate("expense");
        });

    }

    private void updateListName() {

        List<ExpenseDto> collect = expenseClient.getExpenses().stream()
                .filter(e -> e.getName().toLowerCase().contains(nameSearch.getValue().toLowerCase()))
                .collect(Collectors.toList());
        gridExpense.setItems(collect);
    }
    private void updateListType() {

        List<ExpenseDto> collect = expenseClient.getExpenses().stream()
                .filter(e -> e.getType().equals(categorySearch.getValue() != null ? categorySearch.getValue().getName() : expenseClient.getExpenses()))
                .collect(Collectors.toList());
        gridExpense.setItems(collect);
    }


    private void deleteItem(Long id) {

        boolean isDeleted = expenseClient.deleteExpense(id);

        gridExpense.getDataProvider().refreshAll();

        if (isDeleted) {
            showSuccess();
            gridExpense.setItems(expenseClient.getExpenses());
            gridExpense.getDataProvider().refreshAll();
            UI.getCurrent().navigate(ExpensesView.class);
        } else {
            showError();
            UI.getCurrent().navigate(ExpensesView.class);
        }
    }

//    public void updateitem(ExpenseDto getExpense) {
//        expenseDto = getExpense;
//        UI.getCurrent().navigate(EditItemView.class);
//    }

    private void showError() {
        Notification notification = Notification.show("Element nie został poprawnie usunięty!");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void showSuccess( ) {
        Notification notification = Notification.show("Element został usunięty!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
