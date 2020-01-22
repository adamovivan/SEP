package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.ItemType;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;

@Data
public class MembershipDTO {
    private Long id;
    private SubscriptionType subscriptionType;
    private ArticleDTO article;
    private MagazineDTO magazine;
    private ItemType itemType;
}
