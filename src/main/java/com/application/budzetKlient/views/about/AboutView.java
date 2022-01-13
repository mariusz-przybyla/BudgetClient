package com.application.budzetKlient.views.about;

import com.application.budzetKlient.views.MainLayoutView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("O aplikacji")
@Route(value = "about", layout = MainLayoutView.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("Aplikacja kontroli budżetu"));
        add(new H4("Tematem projektu jest „Budżet domowy”. Aplikacja ma na celu wspomóc kontrolowanie wydatków \n" +
                "oraz dać możliwość zaoszczędzenia dodatkowych pieniędzy poprzez nie kupowanie przedmiotów lub \n" +
                "rzeczy, które nie są nam potrzebne. Program będzie miał zastosowanie dla kilku osób jednocześnie. \n" +
                "Każda osoba będzie posiadała swój profil w którym będzie mogła dodawać swoje wydatki.\n"));
        add(new Paragraph("Mariusz Przybyła 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
