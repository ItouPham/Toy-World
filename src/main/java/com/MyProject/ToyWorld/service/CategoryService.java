package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();
    Category findById(Long id);
}
