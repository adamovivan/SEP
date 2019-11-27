package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ScientificField {
    @Id
    private Long id;
    private String name;
}
