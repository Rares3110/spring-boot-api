package com.rares.articlehub.service;

import com.rares.articlehub.model.Category;
import com.rares.articlehub.model.CategoryState;
import com.rares.articlehub.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> findCategoryByTitle(String title) {
        return categoryRepository.findCategoryByTitle(title);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(int id, String title, CategoryState state) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty())
            throw new IllegalStateException("no category with id: " + id);

        categoryOptional.get().setTitle(title);
        categoryOptional.get().setState(state);

        return categoryOptional.get();
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
