package com.foolself.demo.dao;

import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    List<Comment> findAll();
    Comment findById(long id);
    List<Comment> findByArticleId(long id);
    Comment save(Comment comment);

    @Transactional
    @Query(value = "update Comment set content=?1 where id=?2", nativeQuery = true)
    @Modifying
    void update(String content, long id);

    @Transactional
    void deleteById(long id);
}
