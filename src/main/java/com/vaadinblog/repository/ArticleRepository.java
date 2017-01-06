package com.vaadinblog.repository;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import com.vaadinblog.domain.Article;

@Transactional
public interface ArticleRepository extends CrudRepository<Article, Long> {
}