package com.vaadinblog.web;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadinblog.domain.Article;


public class ArticleLayout extends VerticalLayout {

    public ArticleLayout(Article article) {

        Label title=new Label(article.getTitle());
        Label timestamp= new Label(article.getTimestamp().toString());
        Label content= new Label(article.getContent());
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        addComponents(title, timestamp, content);
    }

}
