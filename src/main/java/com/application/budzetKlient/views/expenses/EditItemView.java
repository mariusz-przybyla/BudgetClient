package com.application.budzetKlient.views.expenses;

import com.application.budzetKlient.dto.AddExpenseDto;
import com.application.budzetKlient.dto.ExpenseDto;
import com.application.budzetKlient.dto.IdDto;
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

import java.util.List;
import java.util.logging.Logger;

@PageTitle("Edytuj wydatek")
@Route(value = "edit", layout = MainLayoutView.class)
public class EditItemView extends VerticalLayout {

    Logger logger = Logger.getLogger(EditItemView.class.getName());

    private TextField nameField = new TextField("Nazwa");
    private NumberField priceField = new NumberField("Cena");
    private ComboBox<Category> box = new ComboBox<>("Kategoria");

    private BeanValidationBinder<Expense> binder;

    private Button back = new Button("Cofnij", e -> UI.getCurrent().navigate("expenses"));
    private Button accept = new Button("Edytuj");

    private CategoryClient categoryClient;
    private ExpenseClient expenseClient;
    private Long itemId;
    private IdDto idDto;

    public EditItemView(CategoryClient categoryClient, ExpenseClient expenseClient, LoginClient loginClient, IdDto idDto) {

        this.categoryClient = categoryClient;
        this.expenseClient = expenseClient;
        this.idDto = idDto;

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

        box.setItemLabelGenerator(e -> e.getName());
        box.setItems(categoryClient.getExpenseCategory());

        fillForm(idDto.getId());

        binder = new BeanValidationBinder<Expense>(Expense.class);
        binder.forField(nameField).asRequired().bind("name");
        binder.forField(priceField).asRequired().bind("price");
        binder.forField(box).asRequired().bind("type");

        accept.addClickListener(event -> updateItem(idDto.getId()));
    }

    public void updateItem(Long id) {
        AddExpenseDto expenseDto = new AddExpenseDto();

        try {
            expenseDto.setName(nameField.getValue());
            expenseDto.setPrice(priceField.getValue());
            expenseDto.setCategoryId(box.getValue().getId());
        } catch (Exception e) {
            showError();
            logger.info("Edycja elementu nie powiodła się");
            return;
        }

        boolean ifUpdate = expenseClient.updateElement(id, expenseDto);

        if (ifUpdate) {
            showSuccess();
            UI.getCurrent().navigate(ExpensesView.class);
        } else {
            showError();
            UI.getCurrent().navigate(EditItemView.class);
        }
    }

    private void fillForm(Long id) {

        if (id != null) {
            List<ExpenseDto> expenses = expenseClient.getExpense(id);
            Category category = new Category();
            category.setName(expenses.get(0).getType());

            nameField.setValue(expenses.get(0).getName());
            priceField.setValue(expenses.get(0).getPrice());
        }
    }

    private void showSuccess() {
        Notification notification = Notification.show("Wydatek został zaktualizowany");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void showError() {
        Notification notification = Notification.show("Wszystkie pola muszą być wypełnione!");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
