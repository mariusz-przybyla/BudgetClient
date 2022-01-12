package com.application.budzetKlient.views.expenses;

import com.application.budzetKlient.dto.AddExpenseDto;
import com.application.budzetKlient.dto.ExpenseDto;
import com.application.budzetKlient.model.Category;
import com.application.budzetKlient.model.Expense;
import com.application.budzetKlient.rest.CategoryClient;
import com.application.budzetKlient.rest.ExpenseClient;
import com.application.budzetKlient.rest.LoginClient;
import com.application.budzetKlient.views.MainLayoutView;
import com.application.budzetKlient.views.login.LogoutView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Edytuj wydatek")
@Route(value = "edit", layout = MainLayoutView.class)
public class EditItemView extends VerticalLayout {

    private TextField nameField = new TextField("Nazwa");
    private NumberField priceField = new NumberField("Cena");
    private ComboBox<Category> box = new ComboBox<>("Kategoria");

    private BeanValidationBinder<Expense> binder;

    private Button back = new Button("Cofnij", e -> UI.getCurrent().navigate("expenses"));
    private Button accept = new Button("Edytuj");

    private CategoryClient categoryClient;
    private ExpenseClient expenseClient;
    private ExpenseDto expenseDto = new ExpenseDto();

    public EditItemView(CategoryClient categoryClient, ExpenseClient expenseClient, LoginClient loginClient) {

        this.categoryClient = categoryClient;
        this.expenseClient = expenseClient;
//        this.expenseDto = expenseDto;

        if (loginClient.isNotLogged()) {
            UI.getCurrent().navigate(LogoutView.class);
        }

        H2 title = new H2("Edytuj wydatek");
        accept.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(title, nameField, priceField, box, accept, back);

        formLayout.setMaxWidth("300px");
        formLayout.getStyle().set("margin", "0 auto");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        formLayout.setColspan(title, 2);
        formLayout.setColspan(accept, 1);
        formLayout.setColspan(back, 1);

        add(formLayout);

//        nameField.setValue(expenseDto.getName());
//        priceField.setValue(expenseDto.getPrice());

        binder = new BeanValidationBinder<Expense>(Expense.class);
        binder.forField(nameField).asRequired().bind("name");
        binder.forField(priceField).asRequired().bind("price");
        binder.forField(box).asRequired().bind("type");

        box.setItemLabelGenerator(e -> e.getName());
        box.setItems(categoryClient.getExpenseCategory());

        accept.addClickListener(event -> editItem());
    }

    private void editItem() {
        AddExpenseDto expense = new AddExpenseDto();

        expense.setName(nameField.getValue());
        expense.setPrice(priceField.getValue());
        expense.setCategoryId(box.getValue().getId());

        boolean isAdded = expenseClient.addExpense(expense);

        if (isAdded) {
            UI.getCurrent().navigate(ExpensesView.class);
            showSuccess();
        }

    }

    private void showSuccess() {
        Notification notification = Notification.show("Wydatek został dodany");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
