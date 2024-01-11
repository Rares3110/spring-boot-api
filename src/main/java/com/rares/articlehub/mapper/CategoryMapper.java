package com.rares.articlehub.mapper;

import com.rares.articlehub.dto.CategoryResponse;
import com.rares.articlehub.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse convertCategoryToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle()
        );
    }
}
