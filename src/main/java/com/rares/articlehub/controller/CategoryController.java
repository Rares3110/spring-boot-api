package com.rares.articlehub.controller;

import com.rares.articlehub.dto.CategoryResponse;
import com.rares.articlehub.mapper.CategoryMapper;
import com.rares.articlehub.model.Category;
import com.rares.articlehub.model.CategoryState;
import com.rares.articlehub.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService,
                              CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {
        Optional<Category> categoryOptional = categoryService.findCategoryById(id);

        return categoryOptional.map(category -> ResponseEntity.ok().body(
                categoryMapper.convertCategoryToResponse(category)
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok().body(
                categoryService
                        .findAll()
                        .stream()
                        .map(categoryMapper::convertCategoryToResponse)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/new/{title}")
    public ResponseEntity<CategoryResponse> saveCategory(@PathVariable String title) {
        Optional<Category> categoryOptional = categoryService.findCategoryByTitle(title);
        if(categoryOptional.isPresent())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(
                categoryMapper.convertCategoryToResponse(
                        categoryService.saveCategory(new Category(title, CategoryState.IN_REVIEW))
                )
        );
    }

    @PutMapping("/{id}/")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable int id,
                                                           @RequestParam(required = false) String title,
                                                           @RequestParam(required = false) CategoryState state) {
        Optional<Category> categoryOptional = categoryService.findCategoryById(id);
        if(categoryOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(
                categoryMapper.convertCategoryToResponse(categoryService.updateCategory(id, title, state))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable int id) {
        Optional<Category> categoryOptional = categoryService.findCategoryById(id);
        if(categoryOptional.isEmpty())
            return ResponseEntity.notFound().build();

        categoryService.deleteCategory(categoryOptional.get());
        return ResponseEntity.ok().build();
    }
}
