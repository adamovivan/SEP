package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Data
@Entity
public class ShoppingCart {
    @Id
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany
    private Set<PricelistItem> items;
}
