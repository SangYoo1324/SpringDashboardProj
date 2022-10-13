package com.example.MysqlTest;

import com.example.MysqlTest.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


// entity , id 타입
public interface CommentRepository extends JpaRepository<Comments, Long> {

    //특정 게시글의 모든 댓글 조회
    @Query(value= "SELECT * " +
            "FROM comments " +
            "WHERE article_id = :articleId",  // :는 parameter 하고 같음을 명시
            nativeQuery = true)
    List<Comments> findByArticleId(Long articleId);
    //특정 닉네임의 모든 댓글 조회

    @Query(value= "SELECT * " +
            "FROM comments " +
            "WHERE nickname " +
            "= :nickname",
            nativeQuery = true)
    List<Comments>findByNickname(String nickname);
}
