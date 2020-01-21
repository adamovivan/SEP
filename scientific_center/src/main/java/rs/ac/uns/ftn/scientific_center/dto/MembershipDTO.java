package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;

@Data
public class MembershipDTO {
    private Long id;
    private SubscriptionType subscriptionType;
    private MagazineDTO magazine;
    private ArticleDTO article;
    private UserDTO user;
}
