package com.vaadinblog.web;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadinblog.domain.Article;
import com.vaadinblog.domain.Comment;
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
    
    @Override
    protected void setComments() {
        List <Comment> comments=service.getCommentsByArticleId(article.getId());
        commentSection=new VerticalLayout();
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
            Label commentTitle=new Label("Comment by Anonymous at "+ madeComment.getTimestamp().toString());
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
}
