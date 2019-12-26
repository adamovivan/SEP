package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ShoppingCart {
    @Id
    private Long id;
    @OneToOne
    private User user;
    @ManyToOne
    private PricelistItem item;
}
