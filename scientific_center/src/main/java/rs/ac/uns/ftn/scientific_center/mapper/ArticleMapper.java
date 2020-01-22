package rs.ac.uns.ftn.scientific_center.mapper;

import org.mapstruct.Mapper;
import rs.ac.uns.ftn.scientific_center.dto.ArticleDTO;
import rs.ac.uns.ftn.scientific_center.model.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleDTO articleToArticleDTO(Article article);
}
