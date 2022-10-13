package com.example.MysqlTest.dto;


import com.example.MysqlTest.entity.Article;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ArticleForm {
    private Long id;
    private String title;
    private String content;

    public ArticleForm(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }

    public Article toEntity() {
        return new Article(id, this.title, this.content);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}