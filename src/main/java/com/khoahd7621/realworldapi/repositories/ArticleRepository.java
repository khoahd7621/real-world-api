package com.khoahd7621.realworldapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoahd7621.realworldapi.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    
}
