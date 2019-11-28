package rs.ac.uns.ftn.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.authentication_service.model.Client;

@Repository
public interface RegistrationRepository extends JpaRepository<Client,Long> {
	Client findByUsername(String username);
}
