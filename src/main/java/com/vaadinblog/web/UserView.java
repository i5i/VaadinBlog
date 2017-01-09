package com.vaadinblog.web;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadinblog.domain.Article;
import com.vaadinblog.service.BlogService;

public class UserView extends VerticalLayout implements View {
   private ArticleList articleList;
   private BlogService service;
   
   public UserView(BlogService service){
       this.service=service;
       setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
   }
   
   public void enter(ViewChangeListener.ViewChangeEvent event) {
        removeAllComponents();
        SecurityContext context =  SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            String name = authentication.getName();
            Label labelLogin = new Label("Username: " + name);
            labelLogin.setSizeUndefined();
            addComponent(labelLogin);
            Collection<? extends GrantedAuthority> authorities  = authentication.getAuthorities();
            for (GrantedAuthority ga : authorities){
                String authority = ga.getAuthority();
                if ("ADMIN".equals(authority)){
                    Label lblAuthority = new Label("You are the administrator. ");
                    lblAuthority.setSizeUndefined();
                    addComponent(lblAuthority);
                    addForm();
                }
            }
            Button logout = new Button("Logout");
            LogoutListener logoutListener = new LogoutListener();
            logout.addClickListener(logoutListener);
            BlogUI ui=(BlogUI) UI.getCurrent();
            ui.addMenuButton(logout);
        }else{
            Label notLoggedIn = new Label("Not logged in");
            notLoggedIn.setSizeUndefined();
            addComponent(notLoggedIn);
        }
        addArticleList();
    }
    
    private void addForm() {
        VerticalLayout formLayout=new VerticalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField titleField=new TextField();
        TextArea contentField=new TextArea();
        titleField.setCaption("title");
        titleField.setWidth("80%");
        contentField.setSizeUndefined();
        contentField.setHeight("5em");
        contentField.setWidth("80%");
        contentField.setCaption("article text");
        Button submitButton= new Button("Submit");
        submitButton.addClickListener(ae->{submitArticle(titleField.getValue() ,contentField.getValue());});
        formLayout.addComponents(titleField, contentField, submitButton);
        addComponent(formLayout);
    }
    
    private void addArticleList(){
        articleList=new ArticleList();
        articleList.setWidth("80%");
        articleList.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        articleList.getArticles(service);
        addComponent(articleList);
    }

    private void submitArticle(String title, String content){
        Article article= new Article();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        article.setTimestamp(timestamp);
        article.setTitle(title);
        article.setContent(content);
        articleList.addArticle(article, service);
    }
}
