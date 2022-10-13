package com.example.MysqlTest.api;

import com.example.MysqlTest.ArticleRepository;
import com.example.MysqlTest.dto.ArticleForm;
import com.example.MysqlTest.entity.Article;
import com.example.MysqlTest.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    //Get
    @GetMapping("/api/articles")
    public List<Article> index() {

        return articleService.index();// article entity 의 묶음을 db에서 받아옴 from db
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id) {

        return articleService.show(id); // article entity를 db에서 받아옴 from db
    }

    //Post
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { // restApi post Json을 dto로 받아옴 from Json to db
        Article created = articleService.create(dto);
        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // dto 받아온거 article entity 반환하고 entity를 db에 저장까지

    }
    //Patch
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {// restApi post Json을 dto로 받아옴 from Json to db

        Article updated = articleService.update(id, dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated): ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    //Delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
    Article deletedArticle = articleService.delete(id);
        return(deletedArticle != null)? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    //Transaction => fail => roll빽
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null)? ResponseEntity.status(HttpStatus.OK).body(createdList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


    }


    }
