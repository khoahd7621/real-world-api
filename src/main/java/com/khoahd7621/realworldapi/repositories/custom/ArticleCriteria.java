package com.khoahd7621.realworldapi.repositories.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.khoahd7621.realworldapi.entities.Article;
import com.khoahd7621.realworldapi.models.article.dto.ArticleDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArticleCriteria {
    private final EntityManager em;

    public Map<String, Object> findAll(ArticleDTOFilter filter) {
        // Condition 1=1 for 'and'. If don't we need to if 'first' don't have 'and' else otherwise
        StringBuilder query = new StringBuilder(
            "select a from Article a left join a.author au left join a.usersFavorited auf where 1=1");
        Map<String, Object> params = new HashMap<>();
        if (filter.getTag() != null) {
            query.append(" and a.tagList like :tag");
            params.put("tag", "%" + filter.getTag() + "%");
        }
        if (filter.getAuthor() != null) {
            query.append(" and au.username = :author");
            params.put("author", filter.getAuthor());
        }
        if (filter.getFavorited() != null) {
            query.append(" and auf.username = :favorited");
            params.put("favorited", filter.getFavorited());
        }

        // Query for get total rows
        String countQuery = query.toString().replace("select a", "select count(a.id)");
        
        TypedQuery<Article> tQuery = em.createQuery(query.toString(), Article.class);
        Query countQueryEx = em.createQuery(countQuery);

        params.forEach((key, value) -> {
            tQuery.setParameter(key, value);
            countQueryEx.setParameter(key, value);
        });

        tQuery.setFirstResult(filter.getOffset());
        tQuery.setMaxResults(filter.getLimit());
        Long totalArticles = (Long) countQueryEx.getSingleResult();
        List<Article> listArticles = tQuery.getResultList();

        Map<String, Object> results = new HashMap<>();
        results.put("listArticles", listArticles);
        results.put("totalArticles", totalArticles);
        return results;
    }
}
