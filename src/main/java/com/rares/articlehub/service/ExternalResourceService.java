package com.rares.articlehub.service;

import com.rares.articlehub.model.ExternalResource;
import com.rares.articlehub.repository.ExternalResourceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalResourceService {
    public final ExternalResourceRepository externalResourceRepository;

    public ExternalResourceService(ExternalResourceRepository externalResourceRepository) {
        this.externalResourceRepository = externalResourceRepository;
    }

    public Optional<ExternalResource> findById(int id) {
        return externalResourceRepository.findById(id);
    }

    public ExternalResource saveExternalResource(ExternalResource externalResource) {
        return externalResourceRepository.save(externalResource);
    }

    @Transactional
    public ExternalResource updateExternalResource(int id, Integer articleIndex, URL url) {
        Optional<ExternalResource> externalResourceOptional = externalResourceRepository.findById(id);

        if(externalResourceOptional.isEmpty())
            throw new IllegalStateException("external resource with id " + id + " not found");

        if(articleIndex != null)
            externalResourceOptional.get().setArticleIndex(articleIndex);
        if(url != null)
            externalResourceOptional.get().setUrl(url);

        return externalResourceOptional.get();
    }

    public void deleteExternalResource(ExternalResource externalResource) {
        externalResourceRepository.delete(externalResource);
    }
}
