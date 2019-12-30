package rs.ac.uns.ftn.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.paypal_service.model.TransactionData;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionData, Long> {
	TransactionData findByToken(String token);
}
