package com.application.budzetKlient.views.data;

import com.application.budzetKlient.views.MainLayoutView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.flow.component.button.Button;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@PageTitle("Dane")
@Route(value = "data", layout = MainLayoutView.class)
public class DataListView extends Div {

    private Button updateButton = new Button("Aktualizuj dane");

    private HorizontalLayout horizontalLayout = new HorizontalLayout();

    public DataListView() {

        NumberField numberField = new NumberField();
        numberField.setLabel("Wprowadź kwotę ");
        numberField.setWidth("240px");
        numberField.setValue(Double.valueOf(3500));
        numberField.isReadOnly();

        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setLabel("Read-only");
        textField.setValue(String.valueOf(3000));

        horizontalLayout.add(numberField,  updateButton);

        add(horizontalLayout, textField);



    }

}





