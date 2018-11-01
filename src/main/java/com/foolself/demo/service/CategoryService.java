package com.foolself.demo.service;

import com.foolself.demo.entity.Category;

import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/28 9:29
 */
public interface CategoryService {
    List<Category> findAll();
    Category findById(Integer id);
    Category findByName(String name);
}
