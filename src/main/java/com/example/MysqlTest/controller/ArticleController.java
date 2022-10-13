package com.example.MysqlTest.controller;


import com.example.MysqlTest.ArticleRepository;
import com.example.MysqlTest.CommentRepository;
import com.example.MysqlTest.dto.ArticleForm;
import com.example.MysqlTest.dto.CommentsDto;
import com.example.MysqlTest.entity.Article;


import com.example.MysqlTest.entity.Comments;
import com.example.MysqlTest.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired // 객체 주입(스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결)
    private ArticleRepository articleRepository;

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create") //post 방식으로 던지기로 form 태그에 method="post"
    public String createArticle(ArticleForm articleForm){
        log.info(articleForm.toString());


        //dto-> entity
        Article article = articleForm.toEntity();
        log.info(article.toString());

        //Repository stores entity to db
        Article saved =articleRepository.save(article) ;

        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();  // view template이 와야할 자리인데
        // 리다이렉트로 주소가 올 수 있게 할수 있음
        // return "/articles/"+saved.getId();  이건 view template이 아니라 주소라 작동 안함


    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id: " + id);

        //1: id로 데이터를 가져옴
        Article articleEntity =  articleRepository.findById(id).orElse(null);

        //코멘트 데이터도 가져옴
        List<CommentsDto> commentsList= commentsService.commentsList(id);
        log.info("코멘트 dtos 리스트"+commentsList.toString());

        //2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentsDtos", commentsList);
        //3. 보여줄 페이지를 설정

        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 아티클을 가져온다
        List<Article> articleEntityList =articleRepository.findAll();
        //2. 가져온 article 묶음을 view로 전달
        model.addAttribute("articleList",articleEntityList);
        //view page 설정
        return "articles/index";    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 1. 수정할 데이터를 가져오기
        Article  articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터를 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("/articles/update")  // 이동할 페이지 주소 & post method가 들어왔을때
    //어느 컨트롤러 메소드로 와야 하는지 알려주는 주소
    public String update(ArticleForm form){ // form 에 들어있는 input을 받아올 곳
        log.info(form.toString());

        //1.dto -> entity
        Article editedArticleEntity = form.toEntity();
        //2. entity -> db
        //기존 데이터를 가져온다
        Article target = articleRepository.findById(editedArticleEntity.getId()).orElse(null);
        //기존 데이터의 값을 수정
        if(target !=null){
            articleRepository.save(editedArticleEntity);
        }

        //3. redirect to 수정결과 페이지(show)
        return "redirect:/articles/"+editedArticleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    //delete를 수행 할 트리거 버튼 a태그 주소  & delete시 이동될 주소(template은 아님)
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다");

        //1: 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2. 삭제 진행
        if(target !=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "Post Deleted");
        }
        //3: 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
