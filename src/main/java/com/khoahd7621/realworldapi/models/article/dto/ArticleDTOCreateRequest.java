package com.khoahd7621.realworldapi.models.article.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTOCreateRequest {
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
}
