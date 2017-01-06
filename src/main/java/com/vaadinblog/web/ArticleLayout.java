package com.vaadinblog.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadinblog.domain.Article;
import com.vaadinblog.domain.Comment;
import com.vaadinblog.service.BlogService;

public class ArticleLayout extends VerticalLayout {
    @Autowired
    BlogService service;
    
    public ArticleLayout(Article article) {
        Label title=new Label(article.getTitle());
        Label timestamp= new Label(article.getTimestamp().toString());
        Panel post= new Panel();
        Label content= new Label(article.getContent());
        post.setContent(content);
        title.addStyleName(ValoTheme.LABEL_H2);
        
        addComponents(title, timestamp, post);
        setWidth("50%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        //setCommentSection(service.getCommentsByArticleId(article.getId()));
    }
    private void setCommentSection(List<Comment> comments){
        VerticalLayout commentLayout=new VerticalLayout();
        TextArea commentText= new TextArea();
        Button submitComment= new Button();
        comments.forEach(comment->{commentLayout.addComponent(new Label(comment.toString()));});
        commentLayout.addComponent(commentText);
        commentLayout.addComponent(submitComment);
        addComponent(commentLayout);
    }

}
