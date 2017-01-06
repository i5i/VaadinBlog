package com.vaadinblog.web;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadinblog.domain.Article;

@SpringUI
@Theme("valo")
public class BlogUI extends UI{

    private VerticalLayout layout;
    
    @Autowired
    ArticleList articleList;

    @Override
    protected void init(VaadinRequest request) {
        addLayout();
        addHeaders();
        addForm();
        addArticleLayout();
    }
    private void addLayout() {
        layout=new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);        
    }

    private void addHeaders() {
        Label header=new Label("Vaadin Blog");
        header.addStyleName(ValoTheme.LABEL_H1);
        header.setSizeUndefined();
        layout.addComponent(header);
    }
    
    private void addForm() {
        VerticalLayout formLayout=new VerticalLayout();
        formLayout.setWidth("80%");
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField titleField=new TextField();
        TextArea contentField=new TextArea();
        titleField.setCaption("title");
        contentField.setWidth("50%");
        contentField.setHeight("5em");
        contentField.setCaption("article text");
        Button submitButton= new Button("Submit");
        submitButton.addClickListener(ae->{submitArticle(titleField.getValue() ,contentField.getValue());});
        formLayout.addComponents(titleField, contentField, submitButton);
        layout.addComponent(formLayout);
    }
    
    private void addArticleLayout(){
        articleList.setWidth("80%");
        articleList.getArticles();
        layout.addComponent(articleList);
    }
    
    private void submitArticle(String title, String content){
        Article article= new Article();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        article.setTimestamp(timestamp);
        article.setTitle(title);
        article.setContent(content);
        articleList.addArticle(article);
    }

}
