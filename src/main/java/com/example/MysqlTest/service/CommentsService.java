package com.example.MysqlTest.service;

import com.example.MysqlTest.ArticleRepository;
import com.example.MysqlTest.CommentRepository;
import com.example.MysqlTest.dto.CommentsDto;
import com.example.MysqlTest.entity.Article;
import com.example.MysqlTest.entity.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentsDto> commentsList(Long articleId) {
        //entity 조회
//       List<Comments> commentsList= commentRepository.findByArticleId(articleId);
//
//        //변환 entity to dto
//       List<CommentsDto>dtos =  new ArrayList<CommentsDto>();
//
//       commentsList.stream().forEach(c ->{
//           CommentsDto dto = CommentsDto.createCommentsDto(c);
//           dtos.add(dto);
//       } );
        //반환
//        return dtos;

        return commentRepository.findByArticleId(articleId).stream()
                .map(comment -> CommentsDto.createCommentsDto(comment)).collect(Collectors.toList());
    }
@Transactional
    public CommentsDto create(Long articleId, CommentsDto dto) {
    //코멘트가 달릴 게시글 조회 및 예외 발생(throw로 오류로그 출력하고 계속 진행되게함)
   Article article= articleRepository.findById(articleId)
            .orElseThrow(()->new IllegalArgumentException("대상 게시글이 없습니다"));
        //댓글 eintity 생성
    Comments comment = Comments.createComment(dto,article );
        //댓글 entity를 db로 저장
     Comments created =    commentRepository.save(comment);
        //dto로 변경하여 반환


        return CommentsDto.createCommentsDto(created);
    }

    @Transactional
    public CommentsDto update(Long id, CommentsDto dto) {
        //댓글 조회및 예외  발생
       Comments target = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("대상 댓글이 없습니다"));
        //댓글 수정
        target.patch(dto);
        //DB로 갱신
        Comments updated = commentRepository.save(target);
        //댓글 entity -> dto로 변환 및 반환
        return CommentsDto.createCommentsDto(updated);
    }

    @Transactional
    public CommentsDto delete(Long id) {
        //댓글 조회 & 예외 발생
        Comments target  = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("찾으시는 게시글이 없습니다"));
        //댓글 삭제
        commentRepository.delete(target);
        //삭제 댓글을 dto로 반환
        return CommentsDto.createCommentsDto(target);
    }
}
