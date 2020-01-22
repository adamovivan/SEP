package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.scientific_center.dto.ArticleDTO;
import rs.ac.uns.ftn.scientific_center.mapper.ArticleMapper;
import rs.ac.uns.ftn.scientific_center.model.Article;
import rs.ac.uns.ftn.scientific_center.repository.ArticleRepository;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    public ArticleDTO getArticle(Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NullPointerException("Article doesn't exist."));
        return articleMapper.articleToArticleDTO(article);
    }
}
