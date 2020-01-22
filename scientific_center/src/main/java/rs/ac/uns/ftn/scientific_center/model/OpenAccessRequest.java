package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OpenAccessRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private Article article;
    @ManyToOne
    private Membership membership;
    private Boolean active;
}
