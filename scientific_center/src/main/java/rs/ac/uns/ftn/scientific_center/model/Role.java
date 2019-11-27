package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleName roleName;
    @ManyToMany
    private List<User> users;
}
