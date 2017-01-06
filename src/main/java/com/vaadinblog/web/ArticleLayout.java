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
        Label title=new Label(article.getTitle());
        Label timestamp= new Label(article.getTimestamp().toString());
        Panel post= new Panel();
        Label content= new Label(article.getContent());
        post.setContent(content);
        title.addStyleName(ValoTheme.LABEL_H2);
        
        addComponents(title, timestamp, post);
        setWidth("50%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        List <Comment> comments=service.getCommentsByArticleId(article.getId());
        VerticalLayout commentLayout=new VerticalLayout();
        comments.forEach(comment->{commentLayout.addComponent(new Label(comment.getContent()));});
        addComponent(commentLayout);

        VerticalLayout commentForm= new VerticalLayout();
        TextArea commentText= new TextArea();
        commentText.setCaption("add comment");
        Button submitComment= new Button("submit");
        commentForm.addComponent(commentText);
        commentForm.addComponent(submitComment);
        addComponent(commentForm);
        
        submitComment.addClickListener(ae->{
            Timestamp commentTimestamp=new Timestamp(System.currentTimeMillis());
            Comment madeComment= new Comment();
            madeComment.setTimestamp(commentTimestamp);            
            madeComment.setArticle(article.getId());
            madeComment.setContent(commentText.getValue());
            try{
                service.createComment(madeComment);
                commentLayout.addComponent(new Label(madeComment.getContent()));
            }catch(ConstraintViolationException e){
                System.err.println(e);
            }
         });
    }

}
