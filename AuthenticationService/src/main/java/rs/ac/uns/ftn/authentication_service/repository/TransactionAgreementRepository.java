package rs.ac.uns.ftn.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.authentication_service.model.AgreementTransaction;

public interface TransactionAgreementRepository extends JpaRepository<AgreementTransaction, Long> {

	AgreementTransaction findByUuid(String uuid);
}
