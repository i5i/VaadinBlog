package com.vaadinblog.repository;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.vaadinblog.domain.Comment;

@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
public List<Comment> findByArticleid(long id);
}