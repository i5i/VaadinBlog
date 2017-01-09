package com.vaadinblog.web;

import org.hibernate.exception.ConstraintViolationException;

import com.vaadin.ui.Notification;
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
        if(article.getTitle()!=null && !article.getTitle().equals("")){
        try{
            service.createArticle(article);
            addComponent(new AdminArticleLayout( article, service ), 0);
        }catch(ConstraintViolationException e){
            System.err.println(e);
            Notification.show(e.toString());
        }}else{
            Notification.show("Title field is mandatory");
        }
    }

    public void getAdminArticles(BlogService service) {
        service.getArticles().forEach(article ->{
            addComponent(new AdminArticleLayout(article, service), 0);
            }
        );
    }
}
