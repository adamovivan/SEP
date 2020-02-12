package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.scientific_center.dto.ArticleDTO;
import rs.ac.uns.ftn.scientific_center.exception.ForbiddenException;
import rs.ac.uns.ftn.scientific_center.mapper.ArticleMapper;
import rs.ac.uns.ftn.scientific_center.model.Article;
import rs.ac.uns.ftn.scientific_center.repository.ArticleRepository;

import java.util.List;

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

    public ArticleDTO getArticleAuthor(Long articleId){
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NullPointerException("Article doesn't exist."));
        if(!article.getMainAuthor().getUsername().equals(username)){
            throw new ForbiddenException("Not allowed.");
        }
        return articleMapper.articleToArticleDTO(article);
    }

    public List<ArticleDTO> getAllArticles(){
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        List<Article> articles = articleRepository.findArticlesByMainAuthorUsername(username);
        return articleMapper.articlesToArticleDTOs(articles);
    }
}
