package com.foolself.demo.service;

import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.form.ArticleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/24 18:46
 */
public interface ArticleService {
    List<Article> findAll();
    Page<Article> findAllOrderByLikes(Integer page, Integer size);
    Page<Article> findByUser_Uid(Integer uid, Integer page, Integer size) ;
    Page<Article> findByUser_Username(String username, Integer page, Integer size);
    Page<Article> findByCategory_Name(String name, Integer page, Integer size);
    Page<Article> findByContentLike(String query, Pageable pageable);

    // TODO: 2018/11/1 byTag
//    Page<Article> findByTag(String query, Pageable pageable);
    Article findById(long id);
    Article save(ArticleForm articleForm);
    Article update(ArticleForm articleForm);
    void deleteById(long id);
}
