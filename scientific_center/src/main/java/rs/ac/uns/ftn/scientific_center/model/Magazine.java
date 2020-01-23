package rs.ac.uns.ftn.scientific_center.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Magazine {
    @Id
    private Long id;
    private String title;
    private String issn;
    @ManyToMany
    private Set<ScientificField> scientificFields;
    @ManyToMany
    private Set<User> reviewers;
    @ManyToMany
    private Set<Pricelist> pricelists;
    @OneToMany(mappedBy = "magazine")
    private Set<Membership> memberships;
    @OneToOne
    private EditorialBoard editorialBoard;
    @OneToMany(mappedBy = "magazine")
    private Set<Article> articles;
}
