package com.khoahd7621.realworldapi.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @Override
    public Map<String, ArticleDTOResponse> getArticleBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug);

        boolean favorited = false;
        int favoritesCount = 0;
        boolean isFollowing = false;
        User userLoggedIn = userService.getUserLoggedIn();
        if (userLoggedIn != null) {
            // Todo: Check favorited or not

            // Todo: Check favorites count
        
            // Check logged in user followed author of this article or not
            User author = article.getAuthor();
            Set<User> followers = author.getFollowers(); // Get all folowers of author
            for (User u : followers) {
                if (u.getId() == userLoggedIn.getId()) {
                    isFollowing = true;
                    break;
                }
            }
        }
        
        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, favorited, favoritesCount, isFollowing);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);
        return wrapper;
    }
    
}
