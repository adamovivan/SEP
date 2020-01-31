package rs.ac.uns.ftn.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.authentication_service.model.PaymentTransaction;

@Repository
public interface TransactionRepository extends JpaRepository<PaymentTransaction,Long> {
	PaymentTransaction findByUuid(String uuid);
}
