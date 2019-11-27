package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class PricelistItem {
    @Id
    private Long id;
    @Column(nullable = false)
    private Double price;
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    @ManyToOne
    private Magazine magazine;
    @ManyToOne
    private Article article;
    @ManyToMany
    private Set<Pricelist> pricelists;
}
