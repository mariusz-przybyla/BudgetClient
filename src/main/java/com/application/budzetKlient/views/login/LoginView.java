package com.application.budzetKlient.views.login;


import com.application.budzetKlient.rest.LoginClient;
import com.application.budzetKlient.views.expenses.ExpensesView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Logowanie")
@Route(value = "login")
@RouteAlias("")
@CssImport("./styles/views/login/login-view.css")
public class LoginView extends Div {

    private LoginForm loginForm = new LoginForm();
    private LoginClient loginClient;

    public LoginView(LoginClient loginClient) {

        this.loginClient = loginClient;

        getStyle()
                .set("display", "flex")
                .set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        loginForm.setI18n(getInternationalization());
        add(loginForm);

        loginForm.getElement().setAttribute("no-autofocus", "");

        loginForm.addLoginListener(event -> onLoginClickEvent(event));
    }

    private void onLoginClickEvent(AbstractLogin.LoginEvent event) {

        boolean login = loginClient.login(event.getUsername(), event.getPassword());

        if (login) {
            UI.getCurrent().navigate(ExpensesView.class);
        } else {
            showError();
            UI.getCurrent().navigate(LoginView.class);
        }
    }

    private LoginI18n getInternationalization() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Zaloguj się");
        i18nForm.setUsername("Nazwa użytkownika");
        i18nForm.setPassword("Hasło");
        i18nForm.setSubmit("Zaloguj");
        i18nForm.setForgotPassword("Nie masz konta? Zarejestruj się!");
        loginForm.addForgotPasswordListener(event -> UI.getCurrent().navigate(RegistrationView.class));
        i18n.setForm(i18nForm);

        return i18n;
    }

    private void showError() {
        Notification notification = Notification.show("Niepoprawne dane!");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
