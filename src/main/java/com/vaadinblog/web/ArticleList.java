package com.vaadinblog.web;

import java.sql.SQLException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.ui.VerticalLayout;
import com.vaadinblog.domain.Article;
import com.vaadinblog.service.BlogService;
@Component
public class ArticleList extends VerticalLayout {
    @Autowired
    BlogService service;

    public void getArticles() {
        service.getArticles().forEach(article ->{addComponent(new ArticleLayout(article));});     
    }

    public void addArticle(Article article) {
        try{
        service.createArticle(article);
        addComponent(new ArticleLayout(article));
        }catch(ConstraintViolationException e){
        System.err.println(e);
        }
    }
    
    
}
