package com.foolself.demo.dao;

import com.foolself.demo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAll();
    Page<Article> findAll(Pageable pageable);
    Page<Article> findByUser_Uid(Integer uid, Pageable pageable);
    Page<Article> findByUser_Username(String username, Pageable pageable);
    // TODO: 2018/11/1 test
    Page<Article> findByCategory_Name(String name, Pageable pageable);
    Page<Article> findByContentLike(String query, Pageable pageable);
    Article findById(long id);
    Article save(Article article);
    void deleteById(long id);
}
