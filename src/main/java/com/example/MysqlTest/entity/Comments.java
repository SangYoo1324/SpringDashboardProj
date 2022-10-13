package com.example.MysqlTest.entity;

import com.example.MysqlTest.dto.CommentsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // comment가 many article이 one
    @JoinColumn(name = "article_id")// article 데이터가 들어갈 column 이름
    // Article은 entity(table) 이름이므로 테이블 밸류 전체가 아닌 id가 저장됨
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public Comments(Long id, Article article, String nickname, String body) {
        this.id = id;
        this.article = article;
        this.nickname = nickname;
        this.body = body;
    }

    public static Comments createComment(CommentsDto dto, Article article) {
        //예외 처리
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패, 댓글 id는 비어있어야합니다");
        if (dto.getArticleId() != article.getId()) //JSON데이터(dto)의 articleId와 article의 articleId가 다를때
            throw new IllegalArgumentException("JSON데이터(dto)의 articleId와 article의 articleId가 다를때");
        //엔티티 생성 및 반환

        return new Comments(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentsDto dto) {
        //예외 발생
        if (this.id != dto.getId())
            throw new IllegalArgumentException("대상entity와 Json의 아이디가 일치하지 않음");
        //객체를 갱신
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if(dto.getBody() != null)
            this.body = dto.getBody();
        log.info("패치 로그입니다"+dto.toString());
    }

}
