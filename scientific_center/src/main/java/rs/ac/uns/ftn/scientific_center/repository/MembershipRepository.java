package rs.ac.uns.ftn.scientific_center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.scientific_center.model.Membership;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
