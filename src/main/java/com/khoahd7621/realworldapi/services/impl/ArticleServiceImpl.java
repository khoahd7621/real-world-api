package com.khoahd7621.realworldapi.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.khoahd7621.realworldapi.entities.Article;
import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOCreateRequest;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOResponse;
import com.khoahd7621.realworldapi.models.article.mapper.ArticleMapper;
import com.khoahd7621.realworldapi.repositories.ArticleRepository;
import com.khoahd7621.realworldapi.services.ArticleService;
import com.khoahd7621.realworldapi.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Override
    public Map<String, ArticleDTOResponse> createArtile(Map<String, ArticleDTOCreateRequest> articleDTOCreateMap) {
        ArticleDTOCreateRequest articleDTOCreateRequest = articleDTOCreateMap.get("article");
        
        Article article = ArticleMapper.toArticle(articleDTOCreateRequest);
        User currentUser = userService.getUserLoggedIn();
        article.setAuthor(currentUser);

        articleRepository.save(article);

        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, false, 0, false);
        wrapper.put("article", articleDTOResponse);
        return wrapper;
    }
    
}
