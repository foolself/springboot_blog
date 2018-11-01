package com.foolself.demo.service.impl;

import com.foolself.demo.dao.CommentRepository;
import com.foolself.demo.entity.Comment;
import com.foolself.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/24 18:48
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByArticleId(long id) {
        return commentRepository.findByArticleId(id);
    }

    @Override
    public Comment save(Comment comment) {
        comment.setCreated(new Date());
        comment.setLikes(0);
        if (comment.getToUid() == ""){
            comment.setToUid(null);
        }
        return commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) {
        commentRepository.update(comment.getContent(), comment.getId());
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
