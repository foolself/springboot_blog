package com.foolself.demo.dao;

import com.foolself.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    List<Category> findAll();
    Category findById(Integer id);
    Category findByName(String name);
}
