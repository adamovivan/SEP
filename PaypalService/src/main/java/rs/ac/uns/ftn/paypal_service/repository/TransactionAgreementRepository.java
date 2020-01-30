package rs.ac.uns.ftn.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.paypal_service.model.TransactionAgreementData;

public interface TransactionAgreementRepository extends JpaRepository<TransactionAgreementData, Long>{
	TransactionAgreementData findByToken(String token);
}
