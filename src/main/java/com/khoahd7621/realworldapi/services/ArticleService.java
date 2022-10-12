package com.khoahd7621.realworldapi.services;

import java.util.Map;

import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOCreateRequest;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOFilter;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOResponse;

public interface ArticleService {

    public Map<String, ArticleDTOResponse> createArtile(Map<String, ArticleDTOCreateRequest> articleDTOCreateMap);

    public Map<String, ArticleDTOResponse> getArticleBySlug(String slug);

    public Map<String, Object> getListArticles(ArticleDTOFilter filter);
    
}
