package com.example.MysqlTest.dto;

import com.example.MysqlTest.entity.Comments;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class CommentsDto {
    private Long id;

    private Long articleId;// entity에선 private Article임
    private String nickname;
    private String body;

    public CommentsDto(Long id, Long articleId, String nickname, String body) {
        this.id = id;
        this.articleId = articleId;
        this.nickname = nickname;
        this.body = body;
    }

    public static CommentsDto createCommentsDto(Comments c) {
        return  new CommentsDto(
                c.getId(), c.getArticle().getId(),c.getNickname(), c.getBody()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
