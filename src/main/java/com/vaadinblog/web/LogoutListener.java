package com.vaadinblog.web;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

public class LogoutListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
          BlogUI ui=(BlogUI) UI.getCurrent();
          ui.removeMenuButton(clickEvent.getButton());
          ui.addMenuButton(new Button("Login",new ButtonListener("login")));
          SecurityContextHolder.clearContext();
          Navigator navigator = UI.getCurrent().getNavigator();
          navigator.navigateTo("/login");
    }
}
