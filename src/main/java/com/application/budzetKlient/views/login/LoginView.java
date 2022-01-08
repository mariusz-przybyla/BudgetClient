package com.application.budzetKlient.views.login;

import com.application.budzetKlient.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Logowanie")
@Route(value = "login", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class LoginView extends Div {

    private LoginForm loginForm;

    public LoginView() {
        getStyle()
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex")
                .set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        loginForm = new LoginForm();
        loginForm.setI18n(getInternationalization());
        add(loginForm);

        loginForm.getElement().setAttribute("no-autofocus", "");
    }

    private LoginI18n getInternationalization() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Zaloguj się");
        i18nForm.setUsername("Nazwa użytkownika");
        i18nForm.setPassword("Hasło");
        i18nForm.setSubmit("Zaloguj");
        i18nForm.setForgotPassword("Nie masz konta? Zarejestruj się!");
        i18n.setForm(i18nForm);

        return i18n;
    }

}
