package rs.ac.uns.ftn.scientific_center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.scientific_center.dto.ArticleDTO;
import rs.ac.uns.ftn.scientific_center.service.ArticleService;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ArticleDTO>> getAllArticles(){
        return ResponseEntity.ok().body(articleService.getAllArticles());
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long articleId){
        return ResponseEntity.ok().body(articleService.getArticle(articleId));
    }

    @RequestMapping(value = "/author/{articleId}", method = RequestMethod.GET)
    public ResponseEntity<ArticleDTO> getArticleAuthor(@PathVariable Long articleId){
        return ResponseEntity.ok().body(articleService.getArticleAuthor(articleId));
    }
}
