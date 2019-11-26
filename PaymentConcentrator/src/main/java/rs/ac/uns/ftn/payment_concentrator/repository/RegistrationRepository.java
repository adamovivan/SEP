package rs.ac.uns.ftn.payment_concentrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.payment_concentrator.model.Client;

@Repository
public interface RegistrationRepository extends JpaRepository<Client,Long> {
		
	
	
}
