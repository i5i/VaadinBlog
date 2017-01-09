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
    
    public ArticleLayout(Article article, BlogService service) {
        setTitle(article, ValoTheme.LABEL_H2);
        setTimestamp(article);
        setSubmitedContent(article);
        setComments(article, service); 
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    }
    
    private void setTitle(Article article, String theme) {
        Label title=new Label(article.getTitle());
        title.addStyleName(theme);        
        addComponent(title);        
    }
    
    private void setTimestamp(Article article) {
        Label timestamp= new Label(article.getTimestamp().toString());
        addComponent(timestamp);
    }

    private void setSubmitedContent(Article article) {
        Panel post= new Panel();
        Label content= new Label(article.getContent());
        post.setContent(content);
        addComponent(post);
    }

    private void setComments(Article article, BlogService service) {
        List <Comment> comments=service.getCommentsByArticleId(article.getId());
        VerticalLayout commentLayout=new VerticalLayout();
        comments.forEach(comment->{commentLayout.addComponent(new Label(comment.getContent()));});
        addComponent(commentLayout);

        VerticalLayout commentForm= new VerticalLayout();
        TextArea commentText= new TextArea();
        commentText.setCaption("add comment");
        commentText.setWidth("60%");
        Button submitComment= new Button("submit");
        commentForm.addComponents(commentText, submitComment);
        addComponent(commentForm);
        
        submitComment.addClickListener(ae->{
            Timestamp commentTimestamp=new Timestamp(System.currentTimeMillis());
            Comment madeComment= new Comment();
            madeComment.setTimestamp(commentTimestamp);            
            madeComment.setArticle(article.getId());
            madeComment.setContent(commentText.getValue());
            try{
                service.createComment(madeComment);
                commentLayout.addComponents(new Label("Comment by Anonymous at "+ madeComment.getTimestamp().toString()),new Label(madeComment.getContent()));
            }catch(ConstraintViolationException e){
                System.err.println(e);
            }
         });        
    }
}
