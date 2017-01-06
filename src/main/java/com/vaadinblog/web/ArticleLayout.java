package com.vaadinblog.web;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadinblog.domain.Article;
import com.vaadinblog.domain.Comment;

public class ArticleLayout extends VerticalLayout {
    public ArticleLayout(Article article, List<Comment> comments) {
        Label title=new Label(article.getTitle());
        Label timestamp= new Label(article.getTimestamp().toString());
        Label content= new Label(article.getContent());
        title.addStyleName(ValoTheme.LABEL_H2);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        addComponents(title, timestamp, content);
        
    }

}
