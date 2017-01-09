package com.vaadinblog.web;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import com.vaadinblog.domain.Article;
import com.vaadinblog.service.BlogService;

public class ArticleList extends VerticalLayout {

    public void getArticles(BlogService service) {
        service.getArticles().forEach(article ->{
            addComponent(new ArticleLayout(article, service), 0);
            }
        );     
    }
    
    public void addArticle(Article article, BlogService service) {
        try{
            service.createArticle(article);
            addComponent(new AdminArticleLayout( article, service ), 0);
        }catch(ConstraintViolationException e){
            System.err.println(e);
        }
    }

    public void getAdminArticles(BlogService service) {
        service.getArticles().forEach(article ->{
            addComponent(new AdminArticleLayout(article, service), 0);
            }
        );
    }
}
