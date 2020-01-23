package rs.ac.uns.ftn.scientific_center.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.scientific_center.model.PricelistItem;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;

@Repository
public interface PricelistItemRepository extends JpaRepository<PricelistItem, Long> {
    PricelistItem findByMembershipMagazineId(Long magazineId);
    PricelistItem findByMembershipMagazineIdAndMembershipSubscriptionType(Long magazineId, SubscriptionType subscriptionType);
    PricelistItem findByMembershipArticleId(Long articleId);
}
