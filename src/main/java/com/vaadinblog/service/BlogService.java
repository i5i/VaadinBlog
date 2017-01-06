package com.vaadinblog.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadinblog.domain.Article;
import com.vaadinblog.domain.Comment;
import com.vaadinblog.repository.ArticleRepository;
import com.vaadinblog.repository.CommentRepository;
@Service
public class BlogService {
    @Autowired
    ArticleRepository articleRepo;
    @Autowired 
    CommentRepository commentRepo;
    
    public void createArticle(Article article) throws ConstraintViolationException{
    articleRepo.save(article);
    }
    public void createComment(Comment com) throws ConstraintViolationException{
    commentRepo.save(com);
    }

    public List<Article> getArticles() {
        List<Article> list=new ArrayList<Article>();
        Iterator<Article> iterator=articleRepo.findAll().iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        };
        return list;
    }    
    
    public List<Comment> getCommentsByArticleId(long id) {
        List<Comment> list=commentRepo.findByArticleid(id);
        return list;
    }
}
