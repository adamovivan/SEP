package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.ScientificField;

import java.util.List;
import java.util.Set;


@Data
public class MagazineDTO {
    private Long id;
    private String title;
    private String issn;
    private Set<ScientificField> scientificFields;
    private Set<UserDTO> reviewers;
    private Set<ArticleDTO> articles;
}
