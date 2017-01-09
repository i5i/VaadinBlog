package com.vaadinblog.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    
    public void createArticle(Article article){
    articleRepo.save(article);
    }
    public void createComment(Comment com){
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
        return commentRepo.findByArticle(id);    
        }
}
