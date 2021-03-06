package com.vaadinblog.web;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadinblog.domain.Article;
import com.vaadinblog.domain.Comment;
import com.vaadinblog.service.BlogService;

public class AdminArticleLayout extends ArticleLayout {
    private HorizontalLayout buttonLayout;
    
    public AdminArticleLayout(Article article, BlogService service) {
        super(article, service);
    }
    
    @Override
    protected void setSubmitedContent() {
        Panel post= new Panel();
        Label content= new Label(article.getContent());
        post.setContent(content);
        addComponent(post);
        buttonLayout=new HorizontalLayout();
        Button deleteBtn=new Button("Delete");
        buttonLayout.addComponent(deleteBtn);
        deleteBtn.addClickListener(ae->{deleteArticle(article);});
        Button editBtn=new Button("Edit");
        buttonLayout.addComponent(editBtn);
        editBtn.addClickListener(ae->{editArticle(content, article); buttonLayout.removeComponent(editBtn);});
        addComponent(buttonLayout);
    }
    
    @Override
    protected void setComments() {
        List <Comment> comments=service.getCommentsByArticleId(article.getId());
        commentSection=new VerticalLayout();
        commentSection.setSpacing(true);
        comments.forEach(comment->{
            VerticalLayout commentLayout=new VerticalLayout();
            Label commentTitle=new Label("Comment by Anonymous at "+ comment.getTimestamp().toString());
            Label commentContent=new Label(comment.getContent());
            Button commentDeleter=new Button("Delete Comment");
            commentDeleter.addClickListener(ae->{deleteComment(comment);});
            commentLayout.addComponents(commentTitle, commentContent,commentDeleter);
            commentLayout.setData(comment.getId());
            commentSection.addComponent(commentLayout);
            });
        addComponent(commentSection);        
        }
    
    @Override
    protected void submitComment(String content) {
        Timestamp commentTimestamp=new Timestamp(System.currentTimeMillis());
        Comment madeComment= new Comment();
        madeComment.setTimestamp(commentTimestamp);            
        madeComment.setArticle(article.getId());
        madeComment.setContent(content);
        try{
            service.createComment(madeComment);
            VerticalLayout commentLayout=new VerticalLayout();
            Label commentTitle=new Label("Comment by Admin at "+ madeComment.getTimestamp().toString());
            Label commentContent=new Label(madeComment.getContent());
            Button commentDeleter=new Button("Delete Comment");
            commentDeleter.addClickListener(ae->{deleteComment(madeComment);});
            commentLayout.addComponents(commentTitle, commentContent,commentDeleter);
            commentLayout.setData(madeComment.getId());
            commentSection.addComponent(commentLayout);
        }catch(ConstraintViolationException e){
            System.err.println(e);
        }
     }; 
    
    private void deleteComment(Comment comment) {
        service.removeComment(comment);
        commentSection.forEach(component->{
            VerticalLayout commentLayout=(VerticalLayout)component;
            if(commentLayout.getData().equals(comment.getId())){
                commentSection.removeComponent(component);
            }
         });
    }
    
    private void deleteArticle(Article article) {
        service.removeArticle(article);
        removeAllComponents();
    }
    
    private void editArticle(Label content, Article article) {
        TextArea editField= new TextArea();
        editField.setCaption("edit post");
        editField.setValue(article.getContent());
        editField.setSizeUndefined();
        addComponent(editField, 3);
        Button saveBtn=new Button("Save");
        saveBtn.addClickListener(saving->{ 
            article.setContent(editField.getValue());
            service.createArticle(article);
            content.setValue(editField.getValue());
            buttonLayout.removeComponent(saveBtn);
            removeComponent(editField);
            Button editBtn=new Button("Edit");
            buttonLayout.addComponent(editBtn);
            editBtn.addClickListener(ae->{editArticle(content, article); buttonLayout.removeComponent(editBtn);}); 
        });
        buttonLayout.addComponent(saveBtn);
    }
}
