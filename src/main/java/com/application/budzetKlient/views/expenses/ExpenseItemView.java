package com.application.budzetKlient.views.expenses;

import com.application.budzetKlient.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@PageTitle("Dodaj wydatek")
@Route(value = "expense", layout = MainLayout.class)
public class ExpenseItemView extends VerticalLayout {

    private TextField name = new TextField("Nazwa");
    private NumberField price = new NumberField("Cena");
    private ComboBox<String> box = new ComboBox<>("Kategoria");

    public ExpenseItemView() {
        add(name, price, box);
        box.setItems(Arrays.asList("1", "2", "3"));

    }
}
