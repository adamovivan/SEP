package rs.ac.uns.ftn.scientific_center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.scientific_center.model.OpenAccessRequest;

import java.util.List;

@Repository
public interface OpenAccessRequestRepository extends JpaRepository<OpenAccessRequest, Long> {
    List<OpenAccessRequest> findByMembershipIdAndAuthorUsernameAndActive(Long membershipId, String username, Boolean active);
}
