package rs.ac.uns.ftn.scientific_center.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class EditorialBoard {
    @Id
    private Long id;
    @OneToOne
    private User mainEditor;
    @ManyToMany
    private Set<User> scientificFieldEditors;
    @OneToOne
    private Magazine magazine;
}
