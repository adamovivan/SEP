package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Article {
    @Id
    private Long id;
    private String title;
    private String abstractText;
    private String pdfPath;
    private Boolean accepted;
    @ManyToOne
    private User mainAuthor;
    @ManyToMany
    private List<User> coAuthors;
    @ManyToOne
    private ScientificField scientificField;
    @OneToMany
    private List<Membership> memberships;
}
