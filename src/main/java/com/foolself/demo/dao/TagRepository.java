package com.foolself.demo.dao;

import com.foolself.demo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Override
    List<Tag> findAll();
    Tag findById(Integer id);
    Tag findByName(String name);
    Tag save(Tag tag);
    @Transactional
    void deleteByName(String name);
}
