package com.application.budzetKlient.views.login;

import com.application.budzetKlient.model.UserDetails;
import com.application.budzetKlient.model.UserDetailsService;
import com.application.budzetKlient.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Zarejestruj się")
@Route(value = "registration", layout = MainLayout.class)
public class RegistrationView extends VerticalLayout {

    private PasswordField passwordField1= new PasswordField("Hasło");
    private PasswordField passwordField2 = new PasswordField("Powtórz hasło");
    private TextField firstnameField = new TextField("Imię");
    private TextField lastnameField = new TextField("Nazwisko");
    private TextField loginField = new TextField("Nazwa użytkownika");
    private UserDetails detailsBean = new UserDetails();

    private UserDetailsService service;
    private BeanValidationBinder<UserDetails> binder;

    private boolean enablePasswordValidation;

    public RegistrationView(@Autowired UserDetailsService service) {

        this.service = service;

        H2 title = new H2("Rejestracja");

        Span errorMessage = new Span();

        Button submitButton = new Button("Zarejestruj");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(title, firstnameField, lastnameField, loginField, passwordField1, passwordField2, errorMessage, submitButton);

        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        formLayout.setColspan(title, 2);
        formLayout.setColspan(errorMessage, 2);
        formLayout.setColspan(submitButton, 2);

        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        add(formLayout);

        binder = new BeanValidationBinder<UserDetails>(UserDetails.class);

        binder.forField(firstnameField).asRequired().bind("firstname");
        binder.forField(lastnameField).asRequired().bind("lastname");

        binder.forField(passwordField1).asRequired().withValidator(this::passwordValidator).bind("password");

        passwordField2.addValueChangeListener(e -> {
            enablePasswordValidation = true;

            binder.validate();
        });

        binder.setStatusLabel(errorMessage);

        submitButton.addClickListener(e -> {
            try {
                binder.writeBean(detailsBean);

                showSuccess(detailsBean);

            } catch (ValidationException e1) {
                getElement().getText();
            }
        });

    }

    private void showSuccess(UserDetails detailsBean) {
        Notification notification = Notification.show("Dane zapisane, witaj " + detailsBean.getFirstname());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Hasło powinno zawierać co najmniej 8 znaków");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = passwordField2.getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Hasła nie są równe!");
    }
}
