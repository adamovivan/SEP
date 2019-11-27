package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Magazine {
    @Id
    private Long id;
    private String title;
    private String issn;
    @ManyToOne
    private ScientificField scientificField;
    @ManyToMany
    private List<User> reviewers;
    @ManyToMany
    private List<Pricelist> pricelists;
    @OneToMany
    private List<Membership> memberships;
    @OneToOne
    private EditorialBoard editorialBoard;
}
