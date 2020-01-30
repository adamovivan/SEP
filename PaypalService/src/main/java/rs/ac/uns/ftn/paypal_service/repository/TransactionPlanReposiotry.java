package rs.ac.uns.ftn.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.paypal_service.model.TransactionPlanData;

public interface TransactionPlanReposiotry extends JpaRepository<TransactionPlanData, Long> {
	TransactionPlanData findByToken(String token);
	TransactionPlanData findByUsername(String username);
}
