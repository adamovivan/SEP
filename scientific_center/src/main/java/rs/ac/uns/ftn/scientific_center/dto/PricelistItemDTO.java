package rs.ac.uns.ftn.scientific_center.dto;


import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.Article;
import rs.ac.uns.ftn.scientific_center.model.Magazine;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class PricelistItemDTO {
    private Long id;
    private Double price;
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    private MagazineDTO magazine;
    private Article article;
}
