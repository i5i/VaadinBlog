package com.vaadinblog.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginForm extends VerticalLayout {
    private TextField txtLogin = new TextField("Login: ");
    private PasswordField txtPassword = new PasswordField("Password: ");
    private Button btnLogin = new Button("Login");
    private AuthenticationManager manager;
    
    public LoginForm(AuthenticationManager authManager) {
          this.manager=authManager;
          addComponent(txtLogin);
          addComponent(txtPassword);
          addComponent(btnLogin);
          btnLogin.addClickListener(ae->{login();});
    }
    
    private void login() {
        try{
            String username = getTxtLogin().getValue();
            String password = getTxtPassword().getValue();
            UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = manager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            BlogUI current = (BlogUI) UI.getCurrent();
            Layout menuLayout=(current.getMenuLayout());
            menuLayout.forEach(button->{
                if(button.getCaption().equals("Login")){
                    current.removeMenuButton((Button)button);
                    }
                });
            Navigator navigator = current.getNavigator();
            navigator.navigateTo("");
        }catch(AuthenticationException e){
            Notification.show("Authentication failed: " + e.getMessage());
        }    
    }
    public TextField getTxtLogin() {
      return txtLogin;
    }
    public PasswordField getTxtPassword() {
      return txtPassword;
    }
  }