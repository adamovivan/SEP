package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.ScientificField;


@Data
public class MagazineDTO {
    private Long id;
    private String title;
    private String issn;
    private ScientificField scientificField;
}
