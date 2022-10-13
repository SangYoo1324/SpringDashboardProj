package com.example.MysqlTest.service;

import com.example.MysqlTest.ArticleRepository;
import com.example.MysqlTest.dto.ArticleForm;
import com.example.MysqlTest.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service// 서비스 객체를 springboot에 생성: autowired로 객체생성 없이 사용 가능
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
       return  articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article  = dto.toEntity();
        if(article.getId() != null){// id값이 있는채로 json data를받으면 크리에이트가 안되게 변경
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1: 수정용 entity 생성
        Article article = dto.toEntity();
        log.info("id: {}, article:{}", id, article.toString());
        //2.: 수정 대상 entity 조회
        Article target = articleRepository.findById(id).orElse(null);
        //3: 잘못된 요청 처리(대상이 없거나 id가 다른 경우)
        if(target == null || id != article.getId()){
            //400
            log.info("Json데이터 Id와 수정 대상 id가 다릅니다 ! " +
                    "혹은 id에 해당하는 target 데이터가 없습니다" +
                    "  id: {}, article:{}", id, article.toString());
            return null;
        }
        //4: 업데이트 및 정상 응답
        target.patch(article);  // 보강코드:  field가 없는 채로 전달됬을때 ex) {id: 1, content:"sdfasd"} (title이 없음)
        // 원래 db에 있었던 target 을 update 필요한 필드만 보강하는 메서드
        Article updated = articleRepository.save(target);
        return updated;  // article entity 반환하고 entity를 db에 저장까지

    }

    public Article delete(Long id) {
        //1: 대상 entity 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //2: 대상이 없는 경우 처리
        if(target == null){
                return null;
        }
        //3: 대상 삭제 후 응답 반환
        articleRepository.delete(target);
        return target;
    }

    @Transactional  // 해당 메소드가 실행되다 실패하면 메서드 실행 전으로 롤백
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article >articleList=dtos.stream().map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //entity 묶음을 db로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));
        //강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(()->new IllegalArgumentException("결제 실패"));
        // 결과값 반환
        return articleList;
    }
}
