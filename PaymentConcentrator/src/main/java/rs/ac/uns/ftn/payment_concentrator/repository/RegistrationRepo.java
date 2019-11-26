package rs.ac.uns.ftn.payment_concentrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.payment_concentrator.model.Client;

public interface RegistrationRepo extends JpaRepository<Client,Long> {
		
	
	
}
