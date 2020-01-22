package rs.ac.uns.ftn.scientific_center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.scientific_center.model.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByMainAuthorUsername(String username);
    Article findByMagazineId(Long magazineId);
}
