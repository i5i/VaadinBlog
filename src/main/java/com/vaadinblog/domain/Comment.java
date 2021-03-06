package com.vaadinblog.domain;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "COMMENTS" )
public class Comment {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private long id;
    
    private long article;
    private Timestamp timestamp;
    private String content;
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public long getArticle() {
        return article;
    }
    public void setArticle(long articleid) {
        this.article = articleid;
    }
    
}
