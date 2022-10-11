package com.khoahd7621.realworldapi.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article_tbl")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String slug;
    private String title;
    private String description;
    private String body;

    private String tagList;
    private Date createAt;
    private Date updateAt;
    private boolean favorited;
    private int favoritesCount;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public List<String> getTagList() {
        return Arrays.asList(this.tagList.split(";"));
    }

    public void setTagList(List<String> tagList) {
        StringBuilder str = new StringBuilder();
        for (String tag : tagList) {
            str.append(tag).append(";");
        }
        this.tagList = str.substring(0, str.length() - 1).toString();
    }
}
