package com.foolself.demo.service;

import com.foolself.demo.entity.Tag;

import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/28 9:31
 */
public interface TagService {
    List<Tag> findAll();
    List<String> getAllTagName();
    Tag findById(Integer id);
    Tag findByName(String name);
    Tag save(String name);
    List<Tag> getTagList(String tags);
    void deleteByName(String name);
}
