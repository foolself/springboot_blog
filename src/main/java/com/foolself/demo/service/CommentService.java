package com.foolself.demo.service;

import com.foolself.demo.entity.Comment;

import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/24 18:48
 */
public interface CommentService {
    List<Comment> findAll();
    Comment findById(long id);
    List<Comment> findByArticleId(long id);
    Comment save(Comment comment);
    void update(Comment comment);
    void deleteById(long id);
}
