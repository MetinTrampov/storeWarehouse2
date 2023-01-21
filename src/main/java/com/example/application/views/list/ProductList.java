package com.example.application.views.list;

        import com.example.application.data.entity.Contact;
        import com.example.application.data.entity.Products;
        import com.vaadin.flow.component.button.Button;
        import com.vaadin.flow.component.grid.Grid;
        import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
        import com.vaadin.flow.component.orderedlayout.VerticalLayout;
        import com.vaadin.flow.component.textfield.TextField;
        import com.vaadin.flow.data.value.ValueChangeMode;
        import com.vaadin.flow.router.PageTitle;
        import com.vaadin.flow.router.Route;

        import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "Products",layout = MainLayout.class)
@PageTitle("Products | Vaadin CRM")
public class ProductList extends VerticalLayout {
    Grid<Products> grid = new Grid<>(Products.class);
    TextField filterText = new TextField();

    public ProductList() {
        addClassName("products-view");
        setSizeFull();
        configureGrid();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getToolbar(), grid);
    }

    private void configureGrid() {
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns("productName", "description", "buyingPrice","sellingPrice");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("Add contact");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}