package com.example.MysqlTest.api;

import com.example.MysqlTest.dto.CommentsDto;
import com.example.MysqlTest.entity.Comments;
import com.example.MysqlTest.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CommentsApiController {
    @Autowired
    private CommentsService commentsService;

    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentsDto>> commentsList(@PathVariable Long articleId){
        //서비스에게 위임
       List<CommentsDto> dtos = commentsService.commentsList(articleId);
        //결과 응답
        log.info(dtos.toString());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    //댓글 생성
@PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentsDto> create(@PathVariable Long articleId, @RequestBody CommentsDto dto){
     //서비스에게 위임
        CommentsDto createdDto = commentsService.create(articleId,dto);
    //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);

}

    // 댓글 수정
@PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentsDto> update(@PathVariable Long id, @RequestBody CommentsDto dto){
    // 서비스에게 위임
    CommentsDto updatedDto = commentsService.update(id, dto);

    return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    //결과 응답
}
    //댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentsDto> delete(@PathVariable Long id){
        // 서비스에게 위임
        CommentsDto deletedDto = commentsService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
        //결과 응답
    }


}
