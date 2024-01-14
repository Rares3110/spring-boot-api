package com.rares.articlehub.repository;

import com.rares.articlehub.model.ExternalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExternalResourceRepository extends JpaRepository<ExternalResource, Integer> {
}
