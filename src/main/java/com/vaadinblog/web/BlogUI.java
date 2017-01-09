package com.vaadinblog.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadinblog.security.AuthManager;
import com.vaadinblog.service.BlogService;

@SpringUI
@Theme("valo")
public class BlogUI extends UI{
    
    private VerticalLayout rootLayout;
    private Navigator navigator;
    private HorizontalLayout menuLayout;
    private CssLayout contentLayout;
    
    @Autowired
    private BlogService service;
    @Autowired
    private AuthManager authManager;

    @Override
    protected void init(VaadinRequest request) {
        addLayout();
        addHeaders();
        addMenu();
        contentLayout = new CssLayout();
        navigator = new Navigator(this, contentLayout);
        navigator.addView("/login",new LoginView(authManager));
        navigator.addView("",new UserView(service));
        setNavigator(navigator);
        rootLayout.addComponent(contentLayout);
        navigator.navigateTo("");
    }
    
    private void addLayout() {
        rootLayout=new VerticalLayout();
        rootLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(rootLayout);        
    }

    private void addHeaders() {
        Label header=new Label("Vaadin Blog");
        header.addStyleName(ValoTheme.LABEL_H1);
        header.setSizeUndefined();
        rootLayout.addComponent(header);
    }
    
    private void addMenu(){
        menuLayout=new HorizontalLayout();
        menuLayout.addComponent(new Button("Blog", new ButtonListener("")));
        menuLayout.addComponent(new Button("Login", new ButtonListener("login")));
        rootLayout.addComponent(menuLayout);
    }
    void addMenuButton(Button button){
        menuLayout.addComponent(button);
    }
    void removeMenuButton(Button button){
        menuLayout.removeComponent(button);
    }
    HorizontalLayout getMenuLayout(){
        return this.menuLayout;
    }
    
}
