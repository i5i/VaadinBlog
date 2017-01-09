package com.vaadinblog.web;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
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
    protected final Article article;
    protected final BlogService service;
    protected VerticalLayout commentSection;
    
    public ArticleLayout(Article article, BlogService service) {
        this.article=article;
        this.service=service;
        setTitle(ValoTheme.LABEL_H2);
        setTimestamp();
        setSubmitedContent();
        setComments(); 
        setCommentForm();
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    }
    
    protected void setTitle(String theme) {
        Label title=new Label(article.getTitle());
        title.addStyleName(theme);        
        addComponent(title);        
    }
    
    protected void setTimestamp() {
        Label timestamp= new Label(article.getTimestamp().toString());
        addComponent(timestamp);
    }

    protected void setSubmitedContent() {
        Panel post= new Panel();
        Label content= new Label(article.getContent());
        post.setContent(content);
        addComponent(post);
    }

    protected void setComments() {
        List <Comment> comments=service.getCommentsByArticleId(article.getId());
        commentSection=new VerticalLayout();
        comments.forEach(comment->{
            commentSection.addComponents(
                    new Label("Comment by Anonymous at "+ comment.getTimestamp().toString()), 
                    new Label(comment.getContent()));
            });
        addComponent(commentSection);        
        }
    
    protected void setCommentForm(){
        VerticalLayout commentForm= new VerticalLayout();
        TextArea commentText= new TextArea();
        commentText.setCaption("add comment");
        commentText.setWidth("60%");
        Button submitComment= new Button("submit");
        commentForm.addComponents(commentText, submitComment);
        submitComment.addClickListener(ae->{submitComment(commentText.getValue());});
        addComponent(commentForm);
          
    }
    protected void submitComment(String content) {
        Timestamp commentTimestamp=new Timestamp(System.currentTimeMillis());
        Comment madeComment= new Comment();
        madeComment.setTimestamp(commentTimestamp);            
        madeComment.setArticle(article.getId());
        madeComment.setContent(content);
        try{
            service.createComment(madeComment);
            commentSection.addComponents(
                    new Label("Comment by Anonymous at "+ madeComment.getTimestamp().toString()),
                    new Label(madeComment.getContent()));
        }catch(ConstraintViolationException e){
            System.err.println(e);
        }
     };      
    }

