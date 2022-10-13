package com.example.MysqlTest.entity;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Article {
    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;  // 받은 json 데이터 필드에 title값이 있으면 바꿔치기
        }
        if(article.content != null) {
            this.content = article.content;// 받은 json 데이터 필드에 content값이 있으면 바꿔치기
        }
    }
}
