package rs.ac.uns.ftn.scientific_center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.scientific_center.model.AgreementTransaction;

public interface AgreementTransactionRepository extends JpaRepository<AgreementTransaction, Long> {
	AgreementTransaction findByOrderId(String orderId);
}
