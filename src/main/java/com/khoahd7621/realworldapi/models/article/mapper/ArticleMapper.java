package com.khoahd7621.realworldapi.models.article.mapper;

import java.util.Date;

import com.khoahd7621.realworldapi.entities.Article;
import com.khoahd7621.realworldapi.entities.User;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOCreateRequest;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOResponse;
import com.khoahd7621.realworldapi.models.article.dto.AuthorDTOResponse;
import com.khoahd7621.realworldapi.utils.SlugUtil;

public class ArticleMapper {

    public static Article toArticle(ArticleDTOCreateRequest articleDTOCreateRequest) {
        Date now = new Date();
        Article article = Article.builder()
        .slug(SlugUtil.getSlug(articleDTOCreateRequest.getTitle()))
        .title(articleDTOCreateRequest.getTitle())
        .description(articleDTOCreateRequest.getDescription())
        .body(articleDTOCreateRequest.getBody())
        .createAt(now)
        .updateAt(now)
        .favorited(false)
        .favoritesCount(0).build();
        article.setTagList(articleDTOCreateRequest.getTagList());
        return article;
    }

    public static ArticleDTOResponse toArticleDTOResponse(Article article, boolean favorited, int favoritesCount, boolean isFollowing) {
        ArticleDTOResponse articleDTOResponse = ArticleDTOResponse.builder()
        .slug(article.getSlug())
        .title(article.getTitle())
        .description(article.getDescription())
        .body(article.getBody())
        .tagList(article.getTagList())
        .createAt(article.getCreateAt())
        .updateAt(article.getUpdateAt())
        .favorited(favorited)
        .favoritesCount(favoritesCount)
        .author(toAuthorDTOResponse(article.getAuthor(), isFollowing)).build();
        return articleDTOResponse;
    }

    private static AuthorDTOResponse toAuthorDTOResponse(User author, boolean isFollowing) {
        return AuthorDTOResponse.builder()
        .username(author.getUsername())
        .bio(author.getBio())
        .image(author.getImage())
        .following(isFollowing).build();
    }

}
