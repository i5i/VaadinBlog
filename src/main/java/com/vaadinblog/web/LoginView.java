package com.vaadinblog.web;

import org.springframework.security.authentication.AuthenticationManager;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {
    public LoginView(AuthenticationManager authManager) {
        LoginForm loginForm = new LoginForm(authManager);
        addComponent(loginForm);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
};
