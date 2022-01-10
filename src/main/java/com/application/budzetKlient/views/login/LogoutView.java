package com.application.budzetKlient.views.login;

import com.application.budzetKlient.rest.LoginClient;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("logout")
@PageTitle("Wyloguj")
public class LogoutView extends Composite<VerticalLayout> {

    public LogoutView(LoginClient loginClient) {
        UI.getCurrent().navigate(LoginView.class);
        loginClient.logout();
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
    }
}
