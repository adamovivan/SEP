package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.ScientificField;

import java.util.Set;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String abstractText;
    private String pdfPath;
    private Boolean accepted;
    private MembershipSimpleDTO membership;
    private UserDTO mainAuthor;
    private Set<UserDTO> coAuthors;
    private Set<ScientificField> scientificFields;
}
