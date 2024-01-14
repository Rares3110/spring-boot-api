package com.rares.articlehub.mapper;

import com.rares.articlehub.dto.ExternalResourceRequest;
import com.rares.articlehub.dto.ExternalResourceResponse;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.ExternalResource;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
public class ExternalResourceMapper {
    public ExternalResourceResponse convertExternalResourceToResponse(ExternalResource externalResource) throws MalformedURLException {
        return new ExternalResourceResponse(
                externalResource.getId(),
                externalResource.getArticleIndex(),
                externalResource.getUrl(),
                externalResource.getArticle().getId()
        );
    }

    public ExternalResourceResponse convertExternalResourceToEmptyResponse(ExternalResource externalResource) {
        return new ExternalResourceResponse(
                externalResource.getId(),
                externalResource.getArticleIndex(),
                null,
                externalResource.getArticle().getId()
        );
    }

    public ExternalResource convertRequestToExternalResource(ExternalResourceRequest externalResourceRequest, Article article) {
        return new ExternalResource(
                externalResourceRequest.getArticleIndex(),
                externalResourceRequest.getUrl().toString(),
                article
        );
    }
}
