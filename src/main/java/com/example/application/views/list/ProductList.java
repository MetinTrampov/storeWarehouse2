package com.example.application.views.list;

        import com.example.application.data.entity.Contact;
        import com.example.application.data.entity.Products;
        import com.example.application.data.service.CrmService;
        import com.vaadin.flow.component.Component;
        import com.vaadin.flow.component.button.Button;
        import com.vaadin.flow.component.grid.Grid;
        import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
        import com.vaadin.flow.component.orderedlayout.VerticalLayout;
        import com.vaadin.flow.component.textfield.TextField;
        import com.vaadin.flow.data.value.ValueChangeMode;
        import com.vaadin.flow.router.PageTitle;
        import com.vaadin.flow.router.Route;

        import javax.annotation.security.PermitAll;
        import java.util.stream.Stream;

@PermitAll
@Route(value = "Products",layout = MainLayout.class)
@PageTitle("Products | Vaadin CRM")
public class ProductList extends VerticalLayout {

    Grid<Products> grid = new Grid<>(Products.class);
    TextField filterText = new TextField();
    ProductForm pForm;

    private CrmService service;
    public ProductList(CrmService service) {
        this.service = service;
        addClassName("products-view");
        setSizeFull();
        configureGrid();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getToolbar(), grid);

        configurePForm();
        add(
                getPContent()
        );
        closeEditor();
    }
    private void closeEditor() {
        pForm.setProduct(null);
        pForm.setVisible(true);
        removeClassName("editing");

    }


    private void configureGrid() {
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns("productName", "description", "buyingPrice","sellingPrice");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private Component getPContent() {
        HorizontalLayout cont = new HorizontalLayout(grid,pForm);
        cont.setFlexGrow(2,grid);
        cont.setFlexGrow(1,pForm);
        cont.addClassNames("cont");
        cont.setSizeFull();

        return cont;
    }

    private void configurePForm() {
        pForm = new ProductForm(service.findAllCompanies());
        pForm.setWidth("25em");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addProductButton = new Button("Add product");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}