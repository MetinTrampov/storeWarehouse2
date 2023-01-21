package com.example.application.views.list;

import com.example.application.data.entity.Contact;
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

@PageTitle("StoreWarehouse | Vaadin CRM")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout {
    Grid<Contact> grid = new Grid<>(Contact.class);


    TextField filterText = new TextField();
    ContactForm form;
    ProductForm pForm;
    private CrmService service;

    public ListView(CrmService service ) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();
        configurePForm();
        
        

        add(
          getToolbar(),
          getContent(),
                getPContent()
        );

        updateList();
        closeEditor();
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

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");

    }

    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1,form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
             form.setWidth("25em");

             form.addListener(ContactForm.SaveEvent.class, this::saveContact);
             form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
             form.addListener(ContactForm.CloseEvent.class, e-> closeEditor());
    }
    /** That method has function that make button "Save"
     * to do changes to any contact that u choose to edit
     */

    private void saveContact(ContactForm.SaveEvent event){
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }
    /** That method has function that make button "Delete"
     * to delete  any contact that u choose to edit
     */
    private void deleteContact(ContactForm.DeleteEvent event){
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();

    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());


        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(e ->addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    // method which function is to show empty form for adding  new contact
    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        setSizeFull();
        grid.setColumns("firstName","lastName","email");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    /**This method has function to open form witch contains information
     for selected contact and  to edit that contact
     */
    private void editContact(Contact contact) {
        if(contact == null){
            closeEditor();
        }else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
