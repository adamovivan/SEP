package rs.ac.uns.ftn.scientific_center.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.scientific_center.model.Membership;
import rs.ac.uns.ftn.scientific_center.model.SubscriptionType;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
	Membership findByMagazineIdAndSubscriptionType(Long magazineId, SubscriptionType subscriptionType);
    Membership findByMagazineId(Long magazineId);
    Membership findByArticleId(Long articleId);
}
