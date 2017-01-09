package com.vaadinblog.web;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadinblog.domain.Article;
import com.vaadinblog.service.BlogService;

public class AdminArticleLayout extends ArticleLayout {
    public AdminArticleLayout(Article article, BlogService service) {
        super(article, service);
    }
    
    @Override
    protected void setSubmitedContent() {
        Panel post= new Panel();
        Label content= new Label(article.getContent());
        post.setContent(content);
        addComponent(post);
        Button deleteBtn=new Button("Delete");
        addComponent(deleteBtn);
        deleteBtn.addClickListener(ae->{deleteArticle(article);});       
    }

    private void deleteArticle(Article article) {
        service.removeArticle(article);
        removeAllComponents();
    }
}
