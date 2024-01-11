package com.rares.articlehub.service;

import com.rares.articlehub.model.Category;
import com.rares.articlehub.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
}
