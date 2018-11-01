package com.foolself.demo.service.impl;

import com.foolself.demo.dao.TagRepository;
import com.foolself.demo.entity.Tag;
import com.foolself.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/28 9:32
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public List<String> getAllTagName(){
        List<String> tagList = new ArrayList<String>();
        for (Tag tag : tagRepository.findAll()) {
            tagList.add(tag.getName());
        }
        return tagList;
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag save(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getTagList(String tags) {
        List<Tag> tagList = new ArrayList<Tag>();
        for (String s : tags.replaceAll("，", ",").split(",")) {
            Tag tag = this.findByName(s);
            if (tag == null) {
                tag = this.save(s);
            }
            tagList.add(tag);
        }
        return tagList;
    }

    @Override
    public void deleteByName(String name) {
        tagRepository.deleteByName(name);
    }
}
