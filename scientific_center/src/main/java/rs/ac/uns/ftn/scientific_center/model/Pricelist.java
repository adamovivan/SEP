package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Pricelist {
    @Id
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany
    private Set<PricelistItem> pricelistItems;
}
