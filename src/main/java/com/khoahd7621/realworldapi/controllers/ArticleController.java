package com.khoahd7621.realworldapi.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOCreateRequest;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOFilter;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOResponse;
import com.khoahd7621.realworldapi.services.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleService articlesService;

    @PostMapping("")
    public Map<String, ArticleDTOResponse> createArtile(@RequestBody Map<String, ArticleDTOCreateRequest> articleDTOCreateMap)  {
        return articlesService.createArtile(articleDTOCreateMap);
    }

    @GetMapping("/{slug}")
    public Map<String, ArticleDTOResponse> getArticleBySlug(@PathVariable String slug)  {
        return articlesService.getArticleBySlug(slug);
    }

    @GetMapping("")
    public Map<String, Object> getListArticles(
        @RequestParam(name = "tag", required = false) String tag,
        @RequestParam(name = "author", required = false) String author,
        @RequestParam(name = "favorited", required = false) String favorited,
        @RequestParam(name = "limit", defaultValue = "20") Integer limit,
        @RequestParam(name = "offset", defaultValue = "0") Integer offset
    ) {
        ArticleDTOFilter filter = ArticleDTOFilter.builder()
            .tag(tag)
            .author(author)
            .favorited(favorited)
            .limit(limit)
            .offset(offset).build();
        return articlesService.getListArticles(filter);
    }


}
