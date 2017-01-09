package com.vaadinblog.web;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

class ButtonListener implements Button.ClickListener {
    String menuitem;
    public ButtonListener(String menuitem) {
        this.menuitem = menuitem;
    }
    @Override
    public void buttonClick(ClickEvent event) {
        UI current=UI.getCurrent();
        Navigator navigator=current.getNavigator();
        navigator.navigateTo("/" + menuitem);
    }

}
