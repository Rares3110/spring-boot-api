package com.rares.articlehub.repository;

import com.rares.articlehub.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAll(Pageable pageable);
}
