package rs.ac.uns.ftn.scientific_center.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 3000)
    private String abstractText;
    private String pdfPath;
    private Boolean accepted;
    @ManyToOne
    private User mainAuthor;
    @ManyToMany
    private Set<User> coAuthors;
    @ManyToMany
    private Set<ScientificField> scientificFields;
    @OneToOne
    private Membership membership;
    @ManyToOne
    private Magazine magazine;
}
