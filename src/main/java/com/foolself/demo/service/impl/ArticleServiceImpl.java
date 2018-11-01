package com.foolself.demo.service.impl;

import com.foolself.demo.dao.ArticleRepository;
import com.foolself.demo.entity.Article;
import com.foolself.demo.entity.Tag;
import com.foolself.demo.entity.form.ArticleForm;
import com.foolself.demo.service.ArticleService;
import com.foolself.demo.service.CategoryService;
import com.foolself.demo.service.TagService;
import com.foolself.demo.service.UserService;
import com.foolself.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/24 18:47
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> findAllOrderByLikes(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "likes");
        Pageable pageable = PageRequest.of(page, size, sort);
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> findByUser_Uid(Integer uid, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        return articleRepository.findByUser_Uid(uid, pageable);
    }

    @Override
    public Page<Article> findByUser_Username(String username, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        return articleRepository.findByUser_Username(username, pageable);
    }

    @Override
    public Page<Article> findByCategory_Name(String name, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        return articleRepository.findByCategory_Name(name, pageable);
    }

    @Override
    public Page<Article> findByContentLike(String query, Pageable pageable) {
        return articleRepository.findByContentLike("%" + query + "%", pageable);
    }

    @Override
    public Article findById(long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article save(ArticleForm articleForm) {
        Article article = new Article(articleForm.getTitle(), new Date(), new Date(), articleForm.getContent(),
                userService.findByUsername(articleForm.getUsername()),
                categoryService.findByName(articleForm.getCategory()),
                tagService.getTagList(articleForm.getTags()));
        article.setUser(userService.findByUsername(articleForm.getUsername()));
        return articleRepository.save(article);
    }

    @Override
    public Article update(ArticleForm articleForm) {
        Date created = new Date();
        try {
            created = MyUtils.getDate(articleForm.getCreated());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Article article = new Article(articleForm.getTitle(), created, new Date(), articleForm.getContent(),
                userService.findByUsername(articleForm.getUsername()),
                categoryService.findByName(articleForm.getCategory()),
                tagService.getTagList(articleForm.getTags()));
        article.setUser(userService.findByUsername(articleForm.getUsername()));
        article.setId(articleForm.getId());
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }
}