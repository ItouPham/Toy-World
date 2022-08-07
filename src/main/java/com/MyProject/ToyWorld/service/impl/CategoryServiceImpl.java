package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.entity.Category;
import com.MyProject.ToyWorld.repository.CategoryRepository;
import com.MyProject.ToyWorld.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }

}
