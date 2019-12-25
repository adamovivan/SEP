package rs.ac.uns.ftn.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.authentication_service.model.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments,Long> {
	Payments findByUsername(String username);
}
