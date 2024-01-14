package com.rares.articlehub.controller;



import com.rares.articlehub.dto.ExternalResourceModifierRequest;
import com.rares.articlehub.dto.ExternalResourceRequest;
import com.rares.articlehub.dto.ExternalResourceResponse;
import com.rares.articlehub.mapper.ExternalResourceMapper;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.ExternalResource;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.ExternalResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.BooleanControl;
import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping("/resource")
public class ExternalResourceController {
    private final ExternalResourceService externalResourceService;
    private final ArticleService articleService;

    private final ExternalResourceMapper externalResourceMapper;

    public ExternalResourceController(ExternalResourceService externalResourceService,
                                      ArticleService articleService,
                                      ExternalResourceMapper externalResourceMapper) {
        this.externalResourceService = externalResourceService;
        this.articleService = articleService;
        this.externalResourceMapper = externalResourceMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExternalResourceResponse> findExternalResourceById(@PathVariable int id) {
        Optional<ExternalResource> externalResourceOptional = externalResourceService.findById(id);

        if(externalResourceOptional.isEmpty())
            return ResponseEntity.notFound().build();

        try {
            ExternalResourceResponse externalResourceResponse = externalResourceMapper.convertExternalResourceToResponse(externalResourceOptional.get());
            return ResponseEntity.ok().body(externalResourceResponse);
        } catch (MalformedURLException e) {
            return ResponseEntity.ok().body(externalResourceMapper.convertExternalResourceToEmptyResponse(externalResourceOptional.get()));
        }
    }

    @PostMapping("/new/")
    public ResponseEntity<ExternalResourceResponse> saveExternalResource(@RequestBody ExternalResourceRequest externalResourceRequest) {
        Optional<Article> articleOptional = articleService.findArticleById(externalResourceRequest.getArticleId());

        if(articleOptional.isEmpty())
            return ResponseEntity.badRequest().build();

        try {
            return ResponseEntity.ok().body(
                    externalResourceMapper.convertExternalResourceToResponse(
                        externalResourceService.saveExternalResource(
                            externalResourceMapper.convertRequestToExternalResource(externalResourceRequest, articleOptional.get())
                        )
                    )
            );
        }
        catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExternalResourceResponse> updateExternalResource (@PathVariable int id,
            @RequestBody ExternalResourceModifierRequest externalResourceModifierRequest) throws MalformedURLException {
        Optional<ExternalResource> externalResourceOptional = externalResourceService.findById(id);

        if(externalResourceOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(
                externalResourceMapper.convertExternalResourceToResponse(
                    externalResourceService.updateExternalResource(
                            id,
                            externalResourceModifierRequest.getArticleIndex(),
                            externalResourceModifierRequest.getUrl()
                    )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteExternalResource (@PathVariable int id) {
        Optional<ExternalResource> externalResourceOptional = externalResourceService.findById(id);

        if(externalResourceOptional.isEmpty())
            return ResponseEntity.notFound().build();

        externalResourceService.deleteExternalResource(externalResourceOptional.get());
        return ResponseEntity.ok().build();
    }
}
