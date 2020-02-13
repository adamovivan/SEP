package rs.ac.uns.ftn.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.authentication_service.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
	Client findByUsername(String username);
	Client findByEmail(String email);
	Optional<Client> findByCompanyName(String companyName);
	Optional<Client> findByPhoneNumber(String phoneNumber);

}
