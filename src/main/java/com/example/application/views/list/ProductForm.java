package com.example.application.views.list;

        import com.example.application.data.entity.Company;
        import com.example.application.data.entity.Status;
        import com.vaadin.flow.component.Key;
        import com.vaadin.flow.component.button.Button;
        import com.vaadin.flow.component.button.ButtonVariant;
        import com.vaadin.flow.component.combobox.ComboBox;
        import com.vaadin.flow.component.formlayout.FormLayout;
        import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
        import com.vaadin.flow.component.textfield.EmailField;
        import com.vaadin.flow.component.textfield.TextField;

        import java.util.List;

public class ProductForm extends FormLayout {
    TextField productName = new TextField("Product name");
    TextField description = new TextField("Description");
    TextField buyingPrice = new TextField("buyingPrice");
    TextField sellingPrice = new TextField("sellingPrice");

    ComboBox<Company> company = new ComboBox<>("Company");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ProductForm(List<Company> companies) {
        addClassName("product-form");

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);

        add(productName,
                description,
                buyingPrice,
                sellingPrice,
                company,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}